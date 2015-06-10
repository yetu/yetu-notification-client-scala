# yetu-notification-client-scala
Scala client for yetu notifications

After making changes, push all commits, then release to bintray with:

```
# make sure to use java 7, not java 8 for publishing, for the time being.
export JAVA_HOME=`/usr/libexec/java_home -v 1.7`
java -version # this must give you java 7 now, otherwise please install it
sbt "cross release with-defaults"
```

## Set up a vagrant box with rabbitmq

You need to have installed locally:

* [vagrant](https://www.vagrantup.com/)
* [virtualbox](https://www.virtualbox.org/wiki/Downloads)

Then just execute the following:

```
cd vagrant && vagrant up
```

*Note that this will enable port forwarding for ports*
 
 * `5672` - rabbitmq
 * `15672` - rabbitmq admin console
 
If you have some local process running on these ports please change the host ports in the `vagrant/Vagrantfile`.

Go grab a cup of tea or lunch as this might take 10-20 minutes depending on internet speeds.


### Debug Rabbitmq

Once vagrant is up, you can log in to the rabbitmq management console at `http://localhost:15672` using the username/password as specified in [vagrant/group_vars/all/rabbitmq.yml](vagrant/group_vars/all/rabbitmq.yml)

