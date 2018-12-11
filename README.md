# dataprovider
DataProvider is a small Service caching and serving data into Key/Value format


### To launch compilation compilation and unit tests
```
mvn package
```

### To build and start containers
```
docker-compose up --build
```

### Example of Call Save Data
```
curl -X POST  localhost:8383/v1/key/data/value/provider
```

### Example of Call to get Data
```
curl -i -H "Accept: application/json"  -X GET localhost:8383/v1/key/test
```

### Security
We don't accept saving data being empty or having length exceeding 64 cheracters (403 HTTP status is returned)

### Recommendations
Only port 8383 shall be exposed (NAT) to public IP (to hide mongo port)

### areas for improvement
- Stored Values are String, Ideally it shall be string
- Avoid passing Value in arguments.
- Use Put HTTP call to build progressively data.

### areas for improvement
- add custom repository class to add more functionalities to the native Mongo Repository or change default behavior
- extract logic/functional code (implemented in this version inside controller) to Service implementation class.
