# yetu-notification-client-scala
Scala client for yetu notifications

## Setup vagrant box with rabbitmq

You need to have installed locally:

* [pip](https://bootstrap.pypa.io/get-pip.py) (only if you don't have ansible)
* ansible (install via `sudo pip install ansible` or see [this link](https://github.com/ansible/ansible))
* [virtualbox](https://www.virtualbox.org/wiki/Downloads)
* [vagrant](https://www.vagrantup.com/)

Then just execute the following:

```
cd vagrant && vagrant up
```

*Note that this will enable port forwarding for ports*
 
 * `5672` - rabbitmq
 * `15672` - rabbitmq admin console
 
If you have some local process running on these ports please change the host ports in the `vagrant/Vagrantfile`.

Go grab a cup of tea or lunch as this might take up to 20 minutes depending on internet speeds.


### Debug Rabbitmq

Once vagrant is up, you can log in to the rabbitmq management console at `http://localhost:15672` using the username/password as specified in [vagrant/group_vars/all/rabbitmq.yml](vagrant/group_vars/all/rabbitmq.yml)

