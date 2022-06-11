package br.com.cotiinformatica.controllers;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.cotiinformatica.entities.Fornecedor;
import br.com.cotiinformatica.repositories.IFornecedorRepository;
import br.com.cotiinformatica.requests.FornecedorPostRequest;
import br.com.cotiinformatica.requests.FornecedorPutRequest;

@Transactional
@Controller
public class FornecedoresController {

	@Autowired // Inicialização automática
	private IFornecedorRepository fornecedorRepository;

	@RequestMapping(value = "/api/fornecedores", method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody FornecedorPostRequest request) {

		try {

			Fornecedor fornecedor = new Fornecedor();
			fornecedor.setNome(request.getNome());
			fornecedor.setCnpj(request.getCnpj());

			fornecedorRepository.save(fornecedor);

			return ResponseEntity.status(HttpStatus.CREATED).body("Fornecedor cadastrado com sucesso.");
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@RequestMapping(value = "/api/fornecedores", method = RequestMethod.PUT)
	public ResponseEntity<String> put(@RequestBody FornecedorPutRequest request) {

		try {

			Fornecedor fornecedor = new Fornecedor();
			fornecedor.setIdFornecedor(request.getIdFornecedor());
			fornecedor.setNome(request.getNome());
			fornecedor.setCnpj(request.getCnpj());

			fornecedorRepository.save(fornecedor);

			return ResponseEntity.status(HttpStatus.OK).body("Fornecedor atualizado com sucesso.");
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@RequestMapping(value = "/api/fornecedores/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("id") Integer id) {

		try {

			// consultando o fornecedor atraves do id
			Optional<Fornecedor> consulta = fornecedorRepository.findById(id);

			// verificar se o fornecedor foi encontrado
			if (consulta.isPresent()) {

				// excluindo o fornecedor
				Fornecedor fornecedor = consulta.get();
				fornecedorRepository.delete(fornecedor);

				return ResponseEntity.status(HttpStatus.OK).body("Fornecedor excluído com sucesso.");
			} else {

				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Fornecedor não encontrado, verifique o ID informado.");
			}
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@RequestMapping(value = "/api/fornecedores", method = RequestMethod.GET)
	public ResponseEntity<List<Fornecedor>> getAll() {

		try {

			List<Fornecedor> fornecedores = (List<Fornecedor>) fornecedorRepository.findAll();

			return ResponseEntity.status(HttpStatus.OK).body(fornecedores);
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@RequestMapping(value = "/api/fornecedores/{id}", method = RequestMethod.GET)
	public ResponseEntity<Fornecedor> getById(@PathVariable("id") Integer id) {

		try {

			// consultando o fornecedor atraves do id
			Optional<Fornecedor> consulta = fornecedorRepository.findById(id);

			// verificar se o fornecedor foi encontrado
			if (consulta.isPresent()) {

				Fornecedor fornecedor = consulta.get();

				return ResponseEntity.status(HttpStatus.OK).body(fornecedor);
			} else {

				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

}
