# sevenfood-product-api

docker-compose build
docker-compose build sonarqube
docker-compose up -d
docker-compose logs -f --tail=50000

docker stop $(docker ps -qa)
docker rm $(docker ps -qa)
docker rmi $(docker images -qa) -f

./mvnw clean install test jacoco:report
sudo sysctl -w vm.max_map_count=262144


docker build -t rogeriofontes/sevenfood-production-api:v3 .
docker login
docker push rogeriofontes/sevenfood-production-api:v3

docker pull rogeriofontes/sevenfood-production-api:v3
docker run -p 9924:9924 sevenfood-production-api:v3
====
docker run -u zap -p 8080:8080 -v "$(pwd)/docs/zap_workdir:/zap/wrk" -i ghcr.io/zaproxy/zaproxy:stable zap-api-scan.py -t http://localhost:9914/api/v3/api-docs -f openapi -c zap-rules.conf -r zap_report_antes_op1.html
docker run -u zap -p 8080:8080 -v "$(pwd)/docs/zap_workdir:/zap/wrk" -i ghcr.io/zaproxy/zaproxy:stable zap-api-scan.py -t http://localhost:9914/api/v3/api-docs -f openapi -c zap-rules.conf -r zap_report_antes_op1.html

http://localhost:9914/api/swagger-ui/index.html#/
http://localhost:9914/api/v3/api-docs

docker run -u zap -p 8080:8080 -v "$(pwd)/docs/zap_workdir:/zap/wrk" -i ghcr.io/zaproxy/zaproxy:stable zap-api-scan.py -t http://192.168.100.31:9998/api/v3/api-docs -f openapi -c zap-rules.conf -r zap_report_antes_op1.html
