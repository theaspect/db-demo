
# Build

`./gradlew build docker`

# Run
Run `docker run -p 8080:8080 -t com.example/demo-docker`
See also [docker plugin](https://spring.io/guides/gs/spring-boot-docker/#_summary)

# Urls
http://localhost:8080/
http://localhost:8080/jdbc

# TODO
* Make h2 postgres compatible
* Hibernate
* HQL
* Collections
* Add postgres to docker
* Make two config files h2/postgres
* Add pgsql
* Generate data
* Create data populator
* Load queries from file
* Add Elastic search
* Add mongodb?


# Plan
Вводная
Простой пример
CRUD - SQL
CRUD - ORM
N+1
Lazy Init
Criteria
Сложный SQL
Collection
Elastic

?ETL
http://sankeymatic.com/build/

Berry [3223] Sale
Ask Media [6346] Renewal
Pearl Softwares [6127] Upgrade
Fairiprises [3273] Sale
Icebergarts [4713] Renewal
Fluxystems [3034] Upgrade
Bluetronics [9982] Sale
Wizardman [5122] Renewal
Spriteshade [8960] Upgrade
Driftgate [4628] Sale
Berry [8529] Renewal
Ask Media [1033] Upgrade
Pearl Softwares [9201] Sale
Fairiprises [5256] Renewal
Icebergarts [8803] Upgrade
Fluxystems [4842] Sale
Bluetronics [5589] Renewal
Wizardman [2421] Upgrade
Spriteshade [2937] Sale
Driftgate [6194] Renewal

Sale [3223] John Woo
Renewal [6346] Pain Filler
Upgrade [6127] Jeff Dean
Sale [3273] Tom Noname
Renewal [4713] John Woo
Upgrade [3034] Pain Filler
Sale [9982] Jeff Dean
Renewal [5122] Tom Noname
Upgrade [8960] John Woo
Sale [4628] Pain Filler
Renewal [8529] Jeff Dean
Upgrade [1033] Tom Noname
Sale [9201] John Woo
Renewal [5256] Pain Filler
Upgrade [8803] Jeff Dean
Sale [4842] Tom Noname
Renewal [5589] John Woo
Upgrade [2421] Pain Filler
Sale [2937] Jeff Dean
Renewal [6194] Tom Noname

jdbcTemplate.query("select c.id, c.amount, e.first_name || ' ' || e.last_name from contract c " +
                "join employee e on c.account_id = e.id", 
        (rs, num) -> Arrays.asList(rs.getLong(1), rs.getLong(2), rs.getString(3)).toString())
        
jdbcTemplate.query("select cl.name, c.amount, c.id from contract c " +
                "join client cl on c.client_id = cl.id", 
        (rs, num) -> Arrays.asList(rs.getString(1), rs.getLong(2), rs.getLong(3)).toString())
