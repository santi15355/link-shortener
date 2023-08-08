package ru.cutit4me.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.type.descriptor.jdbc.LongVarcharJdbcType;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

import java.util.Date;

import static jakarta.persistence.TemporalType.TIMESTAMP;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "urls")
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @JdbcType(LongVarcharJdbcType.class)
    private String longUrl;

    @NotNull
    @JdbcType(VarcharJdbcType.class)
    private String shortUrl;

    @CreationTimestamp
    @Temporal(TIMESTAMP)
    private Date createdAt;

}
