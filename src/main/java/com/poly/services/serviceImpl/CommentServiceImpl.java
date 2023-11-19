package com.poly.services.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.poly.dao.CommentDAO;
import com.poly.models.Comment;
import com.poly.services.CommentService;

public class CommentServiceImpl implements CommentService {
@Autowired
CommentDAO commentDAO;

@Override
public Comment save(Comment entity) {
	return commentDAO.save(entity);
}

@Override
public List<Comment> saveAll(List<Comment> entities) {
	return (List<Comment>)commentDAO.saveAll(entities);
}

@Override
public <S extends Comment> Optional<S> findOne(Example<S> example) {
	return commentDAO.findOne(example);
}

@Override
public List<Comment> findAll(Sort sort) {
	return commentDAO.findAll(sort);
}

@Override
public void flush() {
	commentDAO.flush();
}

@Override
public Page<Comment> findAll(Pageable pageable) {
	return commentDAO.findAll(pageable);
}

@Override
public <S extends Comment> S saveAndFlush(S entity) {
	return commentDAO.saveAndFlush(entity);
}

@Override
public <S extends Comment> List<S> saveAllAndFlush(Iterable<S> entities) {
	return commentDAO.saveAllAndFlush(entities);
}

@Override
public List<Comment> findAll() {
	return commentDAO.findAll();
}

@Override
public List<Comment> findAllById(List<Integer> ids) {
	return (List<Comment>)commentDAO.findAllById(ids);
}

@Override
public void deleteInBatch(Iterable<Comment> entities) {
	commentDAO.deleteInBatch(entities);
}

@Override
public <S extends Comment> Page<S> findAll(Example<S> example, Pageable pageable) {
	return commentDAO.findAll(example, pageable);
}

@Override
public Optional<Comment> findById(Integer id) {
	return commentDAO.findById(id);
}

@Override
public void deleteAllInBatch(Iterable<Comment> entities) {
	commentDAO.deleteAllInBatch(entities);
}

@Override
public boolean existsById(Integer id) {
	return commentDAO.existsById(id);
}

@Override
public void deleteAllByIdInBatch(Iterable<Integer> ids) {
	commentDAO.deleteAllByIdInBatch(ids);
}

@Override
public <S extends Comment> long count(Example<S> example) {
	return commentDAO.count(example);
}

@Override
public <S extends Comment> boolean exists(Example<S> example) {
	return commentDAO.exists(example);
}

@Override
public void deleteAllInBatch() {
	commentDAO.deleteAllInBatch();
}

@Override
public Comment getOne(Integer id) {
	return commentDAO.getOne(id);
}

@Override
public <S extends Comment, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
	return commentDAO.findBy(example, queryFunction);
}

@Override
public long count() {
	return commentDAO.count();
}

@Override
public void deleteById(Integer id) {
	commentDAO.deleteById(id);
}

@Override
public Comment getById(Integer id) {
	return commentDAO.getById(id);
}

@Override
public void delete(Comment entity) {
	commentDAO.delete(entity);
}

@Override
public Comment getReferenceById(Integer id) {
	return commentDAO.getReferenceById(id);
}

@Override
public void deleteAllById(Iterable<? extends Integer> ids) {
	commentDAO.deleteAllById(ids);
}

@Override
public void deleteAll(Iterable<? extends Comment> entities) {
	commentDAO.deleteAll(entities);
}

@Override
public <S extends Comment> List<S> findAll(Example<S> example) {
	return commentDAO.findAll(example);
}

@Override
public <S extends Comment> List<S> findAll(Example<S> example, Sort sort) {
	return commentDAO.findAll(example, sort);
}

@Override
public void deleteAll() {
	commentDAO.deleteAll();
}


	
}
