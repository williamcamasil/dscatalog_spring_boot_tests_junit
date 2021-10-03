package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.tests.Factory;


//TESTE - CAMADA REPOSITORY
@DataJpaTest //Temos a disposição todos os componentes e infraestrutura do Spring JPA, mas ele não carrega controlador e service
//Usa - se do mesmo (nome do pacote e classe + "Tests") que será feito o teste, nesse caso o pacote repositories da classe ProductRepository
public class ProductRepositoryTests {
	
	//Precisamos do tipo para obter os métodos para a operação 
	//A annotation Autowired é usado, pois estamos fazendo um teste direto do BD, que leva em consideração o arquivo data.sql
	//Nesse caso de teste pode ser usado aqui pois esta usando o @DataJpaTest, porem é necessário ficar atento, pois em outros lugares não pode ser usado
	@Autowired
	private ProductRepository repository;
	
	private long existingId;
	private long nonExistingId;
	private long countTotalProducts;
	
	
	//Conceito de Fixtures - Momento em que o teste será executado
	@BeforeEach
	void setUp() throws Exception {
		//Bastante utilizado, para não precisar ficar criando as variaveis toda vez dentro do bloco de teste
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalProducts = 25L;
	}
	
	//Teste para deletar um Produto quando o ID EXISTE
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		//Fase de Testes AAA
		
		repository.deleteById(existingId);
		//Verifica se o ID foi realmente deletado
		Optional<Product> result = repository.findById(existingId);
		//Teste se existe um objeto dentro do Option, como esse assert é false, deve retornar para ele que o arquivo não foi encontrado, ou seja false
		Assertions.assertFalse(result.isPresent());
		
	}
	
	//Teste para verificar se o save está salvando um novo objeto no repository
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
		//Criado o objeto dessa forma fica mais enxuto
		Product product = Factory.createProduct();
		product.setId(null);
		product = repository.save(product);		
		//Testar se o product getId não é nullo
		Assertions.assertNotNull(product.getId());
		//Testando se o countTotalProducts é igual ao product.getId
		Assertions.assertEquals(countTotalProducts + 1, product.getId());
	}
	
	
	//Teste para deletar um Produto quando o ID NÃO existe
	@Test
	public void deleteShouldThroeEmptyResultDataAccessExceptionWhenIdDoesNotExists() {
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExistingId);
		});
		
	}
	
	@Test
	public void returnShouldReturnProductDoesNotEmptyWhenExistId() {
		Optional<Product> result = repository.findById(existingId);
		Assertions.assertTrue(result.isPresent());
	}
	
	@Test
	public void returnShouldReturnProductEmptyWhenExistId() {
		Optional<Product> result = repository.findById(nonExistingId);
		Assertions.assertTrue(result.isEmpty());
	}
	
}
