FROM library/postgres
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD postgres
ENV POSTGRES_DB Bookstore
COPY create-table.sql /docker-entrypoint-initdb.d/
COPY fill-table.sql /docker-entrypoint-initdb.d/