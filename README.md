
A tool for interactive fault injection testing 
for Apache ZooKeeper based on JBoss Byteman

(M.Sc. project)

How to use it?
--------------

0. build everything: mvn clean install
1. edit config.yml and add your credentials
2. start the test cluster: ./tester launch-cluster config.yml
3. start the test tool ./tester server config.yml
4. go to http://localhost:8080/ and play
5. destroy cluster ./tester destroy-cluster config.yml
...
?. Profit!

License
-------

Apache License v2.0 

