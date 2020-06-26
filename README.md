> docker run --rm --name postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=n2s -e POSTGRES_USER=n2s -p 5432:5432 -d postgres
>
> mvnw clean install flyway:migrate -Pdb
> OR
> mvnw.cmd clean install flyway:migrate -Pdb