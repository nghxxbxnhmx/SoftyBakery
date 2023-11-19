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
import org.springframework.stereotype.Service;

import com.poly.dao.ReviewDAO;
import com.poly.models.Review;
import com.poly.services.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	ReviewDAO reviewDAO;

	@Override
	public  List<Review> getByProductId(int productId) {
		return reviewDAO.getByProductId(productId);
	}

	@Override
	public Double getAvgRating(int productId) {
		return reviewDAO.getAvgRating(productId);
	}
//b
	@Override
	public Review save(Review entity) {
		return reviewDAO.save(entity);
	}
//b
	@Override
	public List<Review>  saveAll(List<Review> entities) {
		return (List<Review>)reviewDAO.saveAll(entities);
	}

	@Override
	public <S extends Review> Optional<S> findOne(Example<S> example) {
		return reviewDAO.findOne(example);
	}

	@Override
	public List<Review> findAll(Sort sort) {
		return reviewDAO.findAll(sort);
	}

	@Override
	public void flush() {
		reviewDAO.flush();
	}

	@Override
	public Page<Review> findAll(Pageable pageable) {
		return reviewDAO.findAll(pageable);
	}

	@Override
	public <S extends Review> S saveAndFlush(S entity) {
		return reviewDAO.saveAndFlush(entity);
	}

	@Override
	public <S extends Review> List<S> saveAllAndFlush(Iterable<S> entities) {
		return reviewDAO.saveAllAndFlush(entities);
	}

	@Override
	public List<Review> findAll() {
		return reviewDAO.findAll();
	}
//b
	@Override
	public List<Review> findAllById(List<Integer> ids) {
		return (List<Review>)reviewDAO.findAllById(ids);
	}

	@Override
	public void deleteInBatch(Iterable<Review> entities) {
		reviewDAO.deleteInBatch(entities);
	}

	@Override
	public <S extends Review> Page<S> findAll(Example<S> example, Pageable pageable) {
		return reviewDAO.findAll(example, pageable);
	}

	@Override
	public Optional<Review> findById(Integer id) {
		return reviewDAO.findById(id);
	}

	@Override
	public void deleteAllInBatch(Iterable<Review> entities) {
		reviewDAO.deleteAllInBatch(entities);
	}

	@Override
	public boolean existsById(Integer id) {
		return reviewDAO.existsById(id);
	}

	@Override
	public void deleteAllByIdInBatch(Iterable<Integer> ids) {
		reviewDAO.deleteAllByIdInBatch(ids);
	}

	@Override
	public <S extends Review> long count(Example<S> example) {
		return reviewDAO.count(example);
	}

	@Override
	public <S extends Review> boolean exists(Example<S> example) {
		return reviewDAO.exists(example);
	}

	@Override
	public void deleteAllInBatch() {
		reviewDAO.deleteAllInBatch();
	}

	@Override
	public Review getOne(Integer id) {
		return reviewDAO.getOne(id);
	}

	@Override
	public <S extends Review, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		return reviewDAO.findBy(example, queryFunction);
	}

	@Override
	public long count() {
		return reviewDAO.count();
	}

	@Override
	public void deleteById(Integer id) {
		reviewDAO.deleteById(id);
	}

	@Override
	public Review getById(Integer id) {
		return reviewDAO.getById(id);
	}

	@Override
	public void delete(Review entity) {
		reviewDAO.delete(entity);
	}

	@Override
	public Review getReferenceById(Integer id) {
		return reviewDAO.getReferenceById(id);
	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		reviewDAO.deleteAllById(ids);
	}

	@Override
	public void deleteAll(List<Review> entities) {
		reviewDAO.deleteAll(entities);
	}

	@Override
	public <S extends Review> List<S> findAll(Example<S> example) {
		return reviewDAO.findAll(example);
	}

	@Override
	public <S extends Review> List<S> findAll(Example<S> example, Sort sort) {
		return reviewDAO.findAll(example, sort);
	}

	@Override
	public void deleteAll() {
		reviewDAO.deleteAll();
	}

	
}
