package com.da2jobu.domain.model.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCompanyId is a Querydsl query type for CompanyId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCompanyId extends BeanPath<CompanyId> {

    private static final long serialVersionUID = 716533444L;

    public static final QCompanyId companyId1 = new QCompanyId("companyId1");

    public final ComparablePath<java.util.UUID> companyId = createComparable("companyId", java.util.UUID.class);

    public QCompanyId(String variable) {
        super(CompanyId.class, forVariable(variable));
    }

    public QCompanyId(Path<? extends CompanyId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCompanyId(PathMetadata metadata) {
        super(CompanyId.class, metadata);
    }

}

