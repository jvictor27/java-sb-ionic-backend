package com.joaovictor.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.joaovictor.cursomc.domain.Categoria;
import com.joaovictor.cursomc.domain.enums.NivelCategoria;
import com.joaovictor.cursomc.domain.enums.Perfil;
import com.joaovictor.cursomc.dto.CategoriaCompletaDTO;
import com.joaovictor.cursomc.dto.CategoriaMostraFilhasDTO;
import com.joaovictor.cursomc.dto.CategoriaSimplesDTO;
import com.joaovictor.cursomc.dto.CategoriaMostraPaiDTO;
import com.joaovictor.cursomc.repositories.CategoriaRepository;
import com.joaovictor.cursomc.services.exceptions.DataIntegrityException;
import com.joaovictor.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		
		if (obj.getCategoriaPai() != null) {
			try {
				obj.setCategoriaPai(find(obj.getCategoriaPai().getId()));
			} catch (ObjectNotFoundException e) {
				throw new ObjectNotFoundException("A categoria pai Id: " + obj.getCategoriaPai().getId() + " não existe.");
			}
		}
		
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos.");
		}
	}
	
	public List<Categoria> findAll() {
		List<Categoria> objs;
		objs = repo.findAll();

		return objs;
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String direction, String orderBy, Integer nivel) {
		if (page > 0) {
			page--;
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.search(nivel, pageRequest);
	}
	
	public Categoria fromDtoCadastro(CategoriaMostraPaiDTO obj) {
		
		Categoria categoria = new Categoria();
		categoria.setNome(obj.getNome());
		NivelCategoria nivelCategoria = null;
		
		if (obj.getCategoriaPai() == null) {
			nivelCategoria = NivelCategoria.PRIMEIRONIVEL;			
		} else {
			Categoria categroiaPai = find(obj.getCategoriaPai().getId());
			nivelCategoria = NivelCategoria.toEnum(categroiaPai.getNivel() + 1);
		categoria.setCategoriaPai(categroiaPai);
		}
		
		categoria.setNivel(nivelCategoria.getCod());
		
		return categoria;
	}
	
	public Categoria toCategoria(CategoriaMostraPaiDTO obj) {
		Categoria categoria = new Categoria();
		categoria.setId(obj.getId());
		categoria = find(categoria.getId());
		
		return categoria;
	}
	
	public CategoriaMostraPaiDTO toCategoriaMostraPaiDTO(Categoria obj) {
		
		CategoriaMostraPaiDTO categoriaMostraPaiDTO = new CategoriaMostraPaiDTO(obj);
		return categoriaMostraPaiDTO;
	}
	
	public CategoriaCompletaDTO toCategoriaCompletaDTO(Categoria obj) {
		
		CategoriaCompletaDTO categoriaCompletaDTO = new CategoriaCompletaDTO(obj);
		return categoriaCompletaDTO;
	}
	
	public CategoriaSimplesDTO toCategoriaSimplesDTO(Categoria obj) {
		
		CategoriaSimplesDTO categoriaSimplesDTO = new CategoriaSimplesDTO(obj);
		return categoriaSimplesDTO;
	}
	
	public CategoriaMostraFilhasDTO toCategoriaMostraFilhaDTO(Categoria obj) {
		
		CategoriaMostraFilhasDTO categoriaMostraFilhaDTO = new CategoriaMostraFilhasDTO(obj);
		return categoriaMostraFilhaDTO;
	}
	
	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}
}
