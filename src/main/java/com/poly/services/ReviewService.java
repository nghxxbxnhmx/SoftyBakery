package com.poly.services;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.poly.models.Review;

public interface ReviewService {

	void deleteAll();

	<S extends Review> List<S> findAll(Example<S> example, Sort sort);

	<S extends Review> List<S> findAll(Example<S> example);

	void deleteAll(List<Review> entities);

	void deleteAllById(Iterable<? extends Integer> ids);

	Review getReferenceById(Integer id);

	void delete(Review entity);

	Review getById(Integer id);

	void deleteById(Integer id);

	long count();

	<S extends Review, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction);

	Review getOne(Integer id);

	void deleteAllInBatch();

	<S extends Review> boolean exists(Example<S> example);

	<S extends Review> long count(Example<S> example);

	void deleteAllByIdInBatch(Iterable<Integer> ids);

	boolean existsById(Integer id);

	void deleteAllInBatch(Iterable<Review> entities);

	Optional<Review> findById(Integer id);

	<S extends Review> Page<S> findAll(Example<S> example, Pageable pageable);

	void deleteInBatch(Iterable<Review> entities);

	List<Review> findAllById(List<Integer> ids);

	List<Review> findAll();

	<S extends Review> List<S> saveAllAndFlush(Iterable<S> entities);

	<S extends Review> S saveAndFlush(S entity);

	Page<Review> findAll(Pageable pageable);

	void flush();

	List<Review> findAll(Sort sort);

	<S extends Review> Optional<S> findOne(Example<S> example);

	List<Review> saveAll(List<Review> entities);

	Review save(Review entity);

	Double getAvgRating(int productId);

	List<Review> getByProductId(int productId);

}
