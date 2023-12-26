package br.com.api.produtos.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.produtos.model.ProdutoModel;
import br.com.api.produtos.model.RespostaModel;
import br.com.api.produtos.service.ProdutoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
//O CrossOrigin permite que a API seja acessada por qualquer domínio inclusive o localhost
@CrossOrigin(origins = "*")
//@RequestMapping
public class ProdutoController {

    @Autowired
    private ProdutoService ps;



    @GetMapping("/listar")
    public Iterable<ProdutoModel> listar() {
        return ps.listar();
    }

    @GetMapping("/")
    public String rota() {
        return "API de produtos está funcionando!";
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody ProdutoModel pm) {
        return ps.cadastrarAlterar(pm, "cadastrar");
    }

    @PutMapping("/alterar")
    public ResponseEntity<?> alterar(@RequestBody ProdutoModel pm) {
        return ps.cadastrarAlterar(pm, "alterar");
    }

    @DeleteMapping("/remover/{codigo}")
    public ResponseEntity<RespostaModel> remover(@PathVariable long codigo) {
        return ps.remover(codigo);
    }

    // @PostMapping("/{acao}")
    // public ResponseEntity<?> postMethodName(
    //     @RequestBody ProdutoModel pm,
    //     @PathVariable String acao
        
    //     ) {

    //     System.out.println(pm);
    //     return ps.cadastrarAlterar(pm, acao);
    // }
    
    





    
}
