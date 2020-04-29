 
FROM postgres:12.2-alpine

COPY postgresql.conf /etc/postgresql/postgresql.conf
COPY init.sql /docker-entrypoint-initdb.d/

RUN mkdir /var/log/postgresql \
    && chmod 666 /var/log/postgresql \
    && chown postgres:postgres /var/log/postgresql

USER postgres

CMD ["postgres", "-c", "config_file=/etc/postgresql/postgresql.conf"]

