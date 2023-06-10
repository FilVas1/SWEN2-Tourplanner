package com.semesterproject.tourplanner.dl;


import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    Optional<T> get(int id);

    List<T> getAll(@Nullable Integer id);


    T create(T t) throws SQLException;

    T update(T t);

    void delete(T t);
}
