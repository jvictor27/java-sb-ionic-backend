package com.joaovictor.cursomc.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.joaovictor.cursomc.domain.Categoria;
import com.joaovictor.cursomc.domain.Produto;
import com.joaovictor.cursomc.dto.CategoriaMostraPaiDTO;
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
		obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
		
		Produto produto = obj.get();
		produto = simplifyProdutoCategorias(produto);
		return produto;
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String direction, String orderBy) {
		if (page > 0) {
			page--;
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = CatRepo.findAllById(ids);
		Page<Produto> produtos = repo.search(nome, categorias, pageRequest);
		
		for (Produto produto : produtos) {
			produto = simplifyProdutoCategorias(produto);
		}
		
		return produtos;
	}
	
	private Produto simplifyProdutoCategorias(Produto obj) {
		if (obj.getCategorias() != null && obj.getCategorias().size() > 0) {
			for (Iterator<Categoria> categoriaIterator = obj.getCategorias().iterator(); categoriaIterator.hasNext();) {
				Categoria categoria = categoriaIterator.next();
				if (categoria.getNivel() != 4) {
					categoriaIterator.remove();
				}
			}
		}
		
		return obj;
	}
	
	public Produto insert(Produto obj) {
		
		if (obj.getCategorias() == null || obj.getCategorias().size() < 1) {
			throw new DataIntegrityException("Cadastre ao menos categoria para o produto");
		}
		
		List<Categoria> listaCategorias = new ArrayList<>();
		
		for (Categoria categoria : obj.getCategorias()) {
			Categoria findCategoria = CatService.find(categoria.getId());
			listaCategorias.add(findCategoria);
			listaCategorias.addAll(addArvoreCategoriaNoProduto(findCategoria));
		}
		
		obj.setCategorias(listaCategorias);
		
		return repo.save(obj);
	}
	
	private List<Categoria> addArvoreCategoriaNoProduto(Categoria categoria) {
		List<Categoria> listaCategorias = new ArrayList<>();
		listaCategorias.add(categoria.getCategoriaPai());
		if (categoria.getCategoriaPai().getCategoriaPai() != null) {
			listaCategorias.addAll(addArvoreCategoriaNoProduto(categoria.getCategoriaPai()));
		}
		
		return listaCategorias;
	}

}
