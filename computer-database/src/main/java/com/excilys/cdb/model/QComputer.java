package com.excilys.cdb.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QComputer is a Querydsl query type for Computer
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QComputer extends EntityPathBase<Computer> {

    private static final long serialVersionUID = 1017758811L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QComputer computer = new QComputer("computer");

    public final QCompany company;

    public final DatePath<java.sql.Date> discontinued = createDate("discontinued", java.sql.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final DatePath<java.sql.Date> introduced = createDate("introduced", java.sql.Date.class);

    public final StringPath name = createString("name");

    public QComputer(String variable) {
        this(Computer.class, forVariable(variable), INITS);
    }

    public QComputer(Path<? extends Computer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QComputer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QComputer(PathMetadata metadata, PathInits inits) {
        this(Computer.class, metadata, inits);
    }

    public QComputer(Class<? extends Computer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
    }

}

