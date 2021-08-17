package com.dio.live.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dio.live.exceptions.DataBaseException;
import com.dio.live.exceptions.ResourceAlreadyExistException;
import com.dio.live.exceptions.ResourceNotFoundException;
import com.dio.live.model.JornadaTrabalho;
import com.dio.live.repository.JornadaTrabalhoRepository;

@Service
public class JornadaTrabalhoService {

	@Autowired
	private JornadaTrabalhoRepository jornadaTrabalhoRepository;

	public Page<JornadaTrabalho> findAllPaged(PageRequest pageRequest) {

		return jornadaTrabalhoRepository.findAll(pageRequest);
	}

	public List<JornadaTrabalho> findAll() {
		return jornadaTrabalhoRepository.findAll();
	}

	@Transactional(readOnly = true)
	public JornadaTrabalho findById(Long id) {

		Optional<JornadaTrabalho> obj = jornadaTrabalhoRepository.findById(id);
		JornadaTrabalho entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
		return entity;
	}

	@Transactional
	public void delete(Long id) {

		try {
			jornadaTrabalhoRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {

			throw new ResourceNotFoundException("Id not found " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity Violation");
		}

	}

	@Transactional
	public JornadaTrabalho insert(JornadaTrabalho jornadaTrabalho) {

		List<JornadaTrabalho> jornadaTrabalhoList = jornadaTrabalhoRepository.findAll();

		for (int i = 0; i < jornadaTrabalhoList.size(); i++) {
			if (jornadaTrabalho.getDescricao().equals(jornadaTrabalhoList.get(i).getDescricao())) {

				throw new ResourceAlreadyExistException("Entity with same description Already exists.");

			}
		}

		return jornadaTrabalhoRepository.save(jornadaTrabalho);

	}

	@Transactional
	public JornadaTrabalho update(Long id, JornadaTrabalho jornadaTrabalho) {

		try {

			JornadaTrabalho entity = jornadaTrabalhoRepository.getOne(id);
			copyDtoToEntity(jornadaTrabalho, entity);
			entity = jornadaTrabalhoRepository.save(entity);
			return entity;

		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}

	}

	private void copyDtoToEntity(JornadaTrabalho jornadaTrabalho, JornadaTrabalho entity) {
		entity.setId(jornadaTrabalho.getId());
		entity.setDescricao(jornadaTrabalho.getDescricao());

	}

}
