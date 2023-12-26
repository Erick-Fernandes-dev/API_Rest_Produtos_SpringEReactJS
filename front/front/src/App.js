import { useEffect, useState } from 'react';
import './App.css';
import Formulario from './Formulario';
import Tabela from './Tabela';

function App() {

  //Objeto produto
  const produto = {
    codigo: 0,
    nome: "",
    marca: ""
  }


  // useState eh um hook que permite criar estados

  // Criando um estado para o botao com hooks
  const [btnCadastrar, setBtnCadastrar] = useState(true);
  const [produtos, setProdutos] = useState([]);
  //deixando o objeto de forma dinamica
  const [objProduto, setObjProduto] = useState(produto);


  // UseEffect eh um hook que permite executar uma funcao sempre que um componente for renderizado
  // ou seja, se comunicar com o backend
  useEffect(() => {
    fetch('http://localhost:8080/listar')
    .then(retorno => retorno.json())
    .then(retorno_convertido => setProdutos(retorno_convertido))
  })

  // obtendo os dados do formulario
  const aoDigitar = (e) => {
    // vai pegar o valor que esta contendo o objProduto
    setObjProduto({...objProduto, [e.target.name]:e.target.value});
  }

  // funcao para cadastrar
  const cadastrar = () => {
    fetch("http://localhost:8080/cadastrar", {
     //  tipo de metodo que sera enviado   
      method:"POST",
      // corpo da requisicao convertido para um texto
      body:JSON.stringify(objProduto),
      headers:{
        "Content-type":"application/json",
        "Accept":"application/json"
      }


    })
    // tipo de retorno promisse que eh uma promessa
    .then(retorno => retorno.json())
    .then(retorno_convertido => {
      
      if (retorno_convertido.mensagem !== undefined) {
        alert(retorno_convertido.mensagem);
      } else {
        setProdutos([...produtos, retorno_convertido])
        alert("Produto cadastrado com sucesso!")
        limparFormulario();
      }

    })
  }


  // funcao para limpar o formulario
  const limparFormulario = () => {
    setObjProduto(produto);
    setBtnCadastrar(true);
  }

  // selecionar produto
  const selecionarProduto = (indice) => {
    setObjProduto(produtos[indice]);
    setBtnCadastrar(false);
  }

  // remover produto
  const remover = () => {
    fetch("http://localhost:8080/remover/"+objProduto.codigo, {
     //  tipo de metodo que sera enviado   
      method:"delete",
      headers:{
        "Content-type":"application/json",
        "Accept":"application/json"
      }


    })
    // tipo de retorno promisse que eh uma promessa
    .then(retorno => retorno.json())
    .then(retorno_convertido => {
      
      // Mensagem
      alert(retorno_convertido.mensagem);

      // Copia do vetor de produtos
      let vetorTemp = [...produtos];

      // Indice
      let indice = vetorTemp.findIndex((p) => {
        return p.codigo === objProduto.codigo;
      });

      // Remover produto do vetorTemp
      vetorTemp.splice(indice, 1);  

      // Atualizar o vetor de produtos
      setProdutos(vetorTemp);

      // Limpar o formulario
      limparFormulario();

    })
  }

   // funcao para alterar produto
   const alterar = () => {
    fetch("http://localhost:8080/alterar", {
     //  tipo de metodo que sera enviado   
      method:"put",
      // corpo da requisicao convertido para um texto
      body:JSON.stringify(objProduto),
      headers:{
        "Content-type":"application/json",
        "Accept":"application/json"
      }


    })
    // tipo de retorno promisse que eh uma promessa
    .then(retorno => retorno.json())
    .then(retorno_convertido => {
      
      if (retorno_convertido.mensagem !== undefined) {
        alert(retorno_convertido.mensagem);
      } else {
        //mensagem
        alert("Produto alterado com sucesso!")

        let vetorTemp = [...produtos];

        // Indice
        let indice = vetorTemp.findIndex((p) => {
          return p.codigo === objProduto.codigo;
        });

        // Remover produto do vetorTemp
        vetorTemp[indice] = objProduto;  

        // Atualizar o vetor de produtos
        setProdutos(vetorTemp);


        limparFormulario();
      }

    })
  }

  return (
    <div>

      {/* <p>{JSON.stringify(produtos)}</p> */}

      {/* <p>{JSON.stringify(objProduto)}</p> */}

      {/* Criando uma propriedade botao */}
      <Formulario botao={btnCadastrar} eventoTeclado={aoDigitar} cadastrar={cadastrar} obj={objProduto} cancelar={limparFormulario} remover={remover} alterar={alterar}/>
      <Tabela vetor={produtos} selecionar={selecionarProduto}/>
    </div>
  );
}

export default App;
