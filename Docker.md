
```bash
# make
docker build -t simulation-world-2d .
# first start
docker run -d --name simulation-world-2d -p 8080:8080 simulation-world-2d
# stop
docker stop simulation-world-2d
# start
docker start simulation-world-2d
# stop and remove
docker rm -f simulation-world-2d
# or
# stop
docker stop simulation-world-2d
# remove
docker rm simulation-world-2d
```