package br.com.api.produtos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.api.produtos.model.ProdutoModel;
import br.com.api.produtos.model.RespostaModel;
import br.com.api.produtos.repository.ProdutoRepository;
import jakarta.persistence.EntityManager;

@Service
public class ProdutoService {


    @Autowired
    private ProdutoRepository pr;

    @Autowired
    private RespostaModel rm;


    //Método para listar todos os produtos
    public Iterable<ProdutoModel> listar() {
        return pr.findAll();
    }

    //Método para cadastrar um produto ou alterar
    public ResponseEntity<?> cadastrarAlterar(ProdutoModel pm, String acao) {
        
        if (pm.getNome().equals("")) {
            rm.setMensagem("O nome do produto é obrigatório!");
            return new ResponseEntity<RespostaModel>(rm, HttpStatus.BAD_REQUEST);
        } else if (pm.getMarca().equals("")) {
            rm.setMensagem("O nome da marca é obrigatório!");
            return new ResponseEntity<>(rm, HttpStatus.BAD_REQUEST);
        
        } else {
            if (acao.equals("cadastrar")) {
                
               // rm.setMensagem("Produto cadastrado com sucesso!");
                return new ResponseEntity<>(pr.save(pm), HttpStatus.CREATED);
            } else {
                //rm.setMensagem("Produto alterado com sucesso!");
                return new ResponseEntity<>(pr.save(pm), HttpStatus.OK);
            }
            // rm.setMensagem("Ação inválida!");
            // return new ResponseEntity<>(rm, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<RespostaModel> remover(long codigo) {
            ProdutoModel pm = pr.findById(codigo).orElse(null);
            if (pm != null) {
                pr.delete(pm);
                rm.setMensagem("Produto removido com sucesso!");
                return new ResponseEntity<RespostaModel>(rm, HttpStatus.OK);
            } else {
                rm.setMensagem("Produto não encontrado!");
                return new ResponseEntity<RespostaModel>(rm, HttpStatus.NOT_FOUND);
            }
        }

    
}
