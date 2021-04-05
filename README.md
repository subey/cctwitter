# cctwiter

Jdk11, endpoint: http://localhost:8080

## Usage
```sh
git clone https://github.com/subey/cctwitter
cd cctwitter/
```

#### Gradle
`./gradlew bootRun`

#### Docker
```sh
docker build -t subey/cctwiter .
docker run -it --rm -p 8080:8080 subey/cctwiter
```
### Doc via swagger-ui
http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
