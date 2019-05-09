package com.excilys.cdb.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAuthority is a Querydsl query type for Authority
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAuthority extends EntityPathBase<Authority> {

    private static final long serialVersionUID = 60582403L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAuthority authority1 = new QAuthority("authority1");

    public final StringPath authority = createString("authority");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QUser username;

    public QAuthority(String variable) {
        this(Authority.class, forVariable(variable), INITS);
    }

    public QAuthority(Path<? extends Authority> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAuthority(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAuthority(PathMetadata metadata, PathInits inits) {
        this(Authority.class, metadata, inits);
    }

    public QAuthority(Class<? extends Authority> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.username = inits.isInitialized("username") ? new QUser(forProperty("username")) : null;
    }

}

