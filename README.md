# Yakshop

Repository containing all the source code for _Yakshop_, the Tundra's first home-grown webshop for all your yak produce.

## Project Kanban Board

[Project Kanban Board](https://trello.com/b/Vzj6KVcx/yakshop)

## Prerequisite
- [Docker](https://docs.docker.com/get-docker/)
- [Maven3](https://maven.apache.org/install.html)
- [java 11 (jre/jdk)](https://www.java.com/en/download/help/download_options.xml)
- A local copy of this repository

```shell
http -> git clone https://github.com/xebia/yakshop-iggydv.git
ssh  -> git clone git@github.com:xebia/yakshop-iggydv.git
```

## API 

**Load new herd**
```shell
curl -X 'POST' \
  'http://localhost:8080/yak-shop/load' \
  -H 'accept: */*' \
  -H 'Content-Type: application/xml' \
  -d '<?xml version="1.0" encoding="UTF-8"?>
<herd>
<labyak name="Betty-1" age="4" sex="f"/>
<labyak name="Betty-2" age="8" sex="f"/>
<labyak name="Betty-3" age="9.5" sex="f"/>
</herd>'
```