package com.joaovictor.cursomc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.joaovictor.cursomc.domain.Categoria;
import com.joaovictor.cursomc.domain.Produto;
import com.joaovictor.cursomc.dto.CategoriaSimplesDTO;
import com.joaovictor.cursomc.dto.ProdutoDTO;
import com.joaovictor.cursomc.repositories.CategoriaRepository;
import com.joaovictor.cursomc.repositories.ProdutoRepository;
import com.joaovictor.cursomc.services.exceptions.DataIntegrityException;
import com.joaovictor.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository CatRepo;
	
	@Autowired
	private CategoriaService CatService;
	
	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = CatRepo.findAllById(ids);
		return repo.search(nome, categorias, pageRequest);
	}
	
	public Produto insert(Produto obj) {
		
		if (obj.getCategorias() == null || obj.getCategorias().size() < 1) {
			System.out.println("Iêêê");
			throw new DataIntegrityException("Cadastre ao menos categoria para o produto");
		}
		
		List<Categoria> listaCategorias = new ArrayList<>();
		
		for (Categoria categoria : obj.getCategorias()) {
			System.out.println(categoria);
			Categoria findCategoria = CatService.find(categoria.getId());
			listaCategorias.add(findCategoria);
			listaCategorias.addAll(addArvoreCategoriaNoProduto(findCategoria));
//			findCategoria.orElseThrow(() -> new ObjectNotFoundException(
//					"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()))
		}
		
		obj.setCategorias(listaCategorias);
		
		return repo.save(obj);
	}
	
	private List<Categoria> addArvoreCategoriaNoProduto(Categoria categoria) {
		List<Categoria> listaCategorias = new ArrayList<>();
//		for (Categoria categoria : obj.getCategorias()) {
//			Categoria findCategoria = CatService.find(categoria.getId());
			listaCategorias.add(categoria.getCategoriaPai());
			if (categoria.getCategoriaPai().getCategoriaPai() != null) {
				listaCategorias.addAll(addArvoreCategoriaNoProduto(categoria.getCategoriaPai()));
			}
//			findCategoria.orElseThrow(() -> new ObjectNotFoundException(
//					"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()))
//		}
		
		return listaCategorias;
	}

}
