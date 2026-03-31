package com.da2jobu.domain.model.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompany is a Querydsl query type for Company
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompany extends EntityPathBase<Company> {

    private static final long serialVersionUID = 1477871219L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCompany company = new QCompany("company");

    public final common.entity.QBaseEntity _super = new common.entity.QBaseEntity(this);

    public final com.da2jobu.domain.model.vo.QCompanyId companyId;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    //inherited
    public final StringPath deletedBy = _super.deletedBy;

    public final com.da2jobu.domain.model.vo.QHubId hubId;

    public final com.da2jobu.domain.model.vo.QLocation location;

    public final StringPath name = createString("name");

    public final EnumPath<com.da2jobu.domain.model.vo.CompanyType> type = createEnum("type", com.da2jobu.domain.model.vo.CompanyType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public QCompany(String variable) {
        this(Company.class, forVariable(variable), INITS);
    }

    public QCompany(Path<? extends Company> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCompany(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCompany(PathMetadata metadata, PathInits inits) {
        this(Company.class, metadata, inits);
    }

    public QCompany(Class<? extends Company> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.companyId = inits.isInitialized("companyId") ? new com.da2jobu.domain.model.vo.QCompanyId(forProperty("companyId")) : null;
        this.hubId = inits.isInitialized("hubId") ? new com.da2jobu.domain.model.vo.QHubId(forProperty("hubId")) : null;
        this.location = inits.isInitialized("location") ? new com.da2jobu.domain.model.vo.QLocation(forProperty("location")) : null;
    }

}

