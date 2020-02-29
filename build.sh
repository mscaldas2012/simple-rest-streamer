APPNAME=simple-rest-streamer
REGISTRY=471108701394.dkr.ecr.us-east-1.amazonaws.com

docker rmi -f arln/$APPNAME
docker rmi -f 471108701394.dkr.ecr.us-east-1.amazonaws.com/arln/$APPNAME
docker build -t arln/$APPNAME .
docker tag arln/$APPNAME:latest $REGISTRY/arln/$APPNAME:latest
$(aws ecr get-login --no-include-email --region us-east-1)
docker push $REGISTRY/arln/$APPNAME:latest
pwd=$(aws ecr get-login --no-include-email --region us-east-1 | awk '{print $6}')

kubectl delete secret eip-registry-secret
kubectl create secret docker-registry eip-registry-secret --docker-server=https://$REGISTRY --docker-username="AWS" --docker-password=$pwd
sed -e "s/<VERSION>/latest/g" ${APPNAME}-deployment.yaml > deployment.yaml
kubectl delete -f deployment.yaml
kubectl create -f deployment.yaml
