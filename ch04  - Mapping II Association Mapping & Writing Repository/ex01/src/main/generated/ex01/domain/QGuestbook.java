package ex01.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGuestbook is a Querydsl query type for Guestbook
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGuestbook extends EntityPathBase<Guestbook> {

    private static final long serialVersionUID = 581417137L;

    public static final QGuestbook guestbook = new QGuestbook("guestbook");

    public final StringPath contents = createString("contents");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final DateTimePath<java.util.Date> regDate = createDateTime("regDate", java.util.Date.class);

    public QGuestbook(String variable) {
        super(Guestbook.class, forVariable(variable));
    }

    public QGuestbook(Path<? extends Guestbook> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGuestbook(PathMetadata metadata) {
        super(Guestbook.class, metadata);
    }

}

