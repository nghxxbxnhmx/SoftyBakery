package com.poly.services;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.poly.models.Comment;

public interface CommentService {

	void deleteAll();

	<S extends Comment> List<S> findAll(Example<S> example, Sort sort);

	<S extends Comment> List<S> findAll(Example<S> example);

	void deleteAll(Iterable<? extends Comment> entities);

	void deleteAllById(Iterable<? extends Integer> ids);

	Comment getReferenceById(Integer id);

	void delete(Comment entity);

	Comment getById(Integer id);

	void deleteById(Integer id);

	long count();

	<S extends Comment, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	Comment getOne(Integer id);

	void deleteAllInBatch();

	<S extends Comment> boolean exists(Example<S> example);

	<S extends Comment> long count(Example<S> example);

	void deleteAllByIdInBatch(Iterable<Integer> ids);

	boolean existsById(Integer id);

	void deleteAllInBatch(Iterable<Comment> entities);

	Optional<Comment> findById(Integer id);

	<S extends Comment> Page<S> findAll(Example<S> example, Pageable pageable);

	void deleteInBatch(Iterable<Comment> entities);

	List<Comment> findAllById(List<Integer> ids);

	List<Comment> findAll();

	<S extends Comment> List<S> saveAllAndFlush(Iterable<S> entities);

	<S extends Comment> S saveAndFlush(S entity);

	Page<Comment> findAll(Pageable pageable);

	void flush();

	List<Comment> findAll(Sort sort);

	<S extends Comment> Optional<S> findOne(Example<S> example);

	List<Comment> saveAll(List<Comment> entities);

	Comment save(Comment entity);

}
