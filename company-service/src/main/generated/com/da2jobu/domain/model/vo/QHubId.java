package com.da2jobu.domain.model.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QHubId is a Querydsl query type for HubId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QHubId extends BeanPath<HubId> {

    private static final long serialVersionUID = -1326870724L;

    public static final QHubId hubId1 = new QHubId("hubId1");

    public final ComparablePath<java.util.UUID> hubId = createComparable("hubId", java.util.UUID.class);

    public QHubId(String variable) {
        super(HubId.class, forVariable(variable));
    }

    public QHubId(Path<? extends HubId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHubId(PathMetadata metadata) {
        super(HubId.class, metadata);
    }

}

