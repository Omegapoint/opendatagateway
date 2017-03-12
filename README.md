# opendatagateway
A gateway to open data.

### Instructions

* Build with `gradle dBI`
* Bring up the containers with `docker-compose up -d`
* Check status on containers with `docker-compose ps`
* Check logs on containers with `docker-compose logs <service>` where service is one of *elasticsearch*, *api* or *ir*.
* Tear down the containers with `docker-compose down`
* Tear down, rebuild and bring up with `docker-compose down && gradle dBI && docker-compose up -d`

The formatting is defined in .editconfig. Be sure to use an ide or editor that supports it. Intellij does out of the box

### Search with the api

So far only matchQueries har supported. With Postman, the Chrome app, one can post queries as application/json
```
//Returns every våtmark in Sweden
{
 "index": "opendatagateway",
 "type": "vatmarker"
}

//Returns a list with våtmarker in Södertälje with Anläggningsår 2003
{
 "index": "opendatagateway",
 "type": "vatmarker",
 "searchTerms": [{"field": "Kommun", "value": "Södertälje"}, {"field":"Anläggningsår", "value":"2003.0"}]
}
```

### Todo
* make it possible to define resources with url, type of resource, name and possibly other properties
* import more resources
* implement a more flexible way of making the queries
* investigate if GraphQL is a way to define queries
