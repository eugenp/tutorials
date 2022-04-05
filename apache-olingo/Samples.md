##  OData test URLs

This following table contains test URLs that can be used with the Olingo V2 demo project.

| URL                                      | Description                                     |
|------------------------------------------|-------------------------------------------------|
| `http://localhost:8080/odata/$metadata`  | fetch OData metadata document |
| `http://localhost:8080/odata/CarMakers?$top=10&$skip=10` | Get 10 entities starting at offset 10 |
| `http://localhost:8080/odata/CarMakers?$count` | Return total count of entities in this set |
| `http://localhost:8080/odata/CarMakers?$filter=startswith(Name,'B')` | Return entities where the *Name* property starts with 'B' |
| `http://localhost:8080/odata/CarModels?$filter=Year eq 2008 and CarMakerDetails/Name eq 'BWM'` | Return *CarModel* entities where the *Name* property of its maker  starts with 'B' |
| `http://localhost:8080/odata/CarModels(1L)?$expand=CarMakerDetails` | Return the *CarModel* with primary key '1', along with its maker|
| `http://localhost:8080/odata/CarModels(1L)?$select=Name,Sku` | Return the *CarModel* with primary key '1', returing only its *Name* and *Sku* properties |
| `http://localhost:8080/odata/CarModels?$orderBy=Name asc,Sku desc` | Return *CarModel* entities, ordered by the their *Name* and *Sku* properties |
| `http://localhost:8080/odata/CarModels?$format=json` | Return *CarModel* entities, using a JSON representation|






