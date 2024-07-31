#!/usr/bin/python2.7

# Copyright 2016 Netflix, Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

import logging

import yaml
from pygenie.client import Genie
from pygenie.conf import GenieConf

logging.basicConfig(level=logging.WARNING)

LOGGER = logging.getLogger(__name__)


def load_yaml(yaml_file):
    with open(yaml_file) as _file:
        return yaml.load(_file)


genie_conf = GenieConf()
genie_conf.genie.url = "http://genie:8080"

genie = Genie(genie_conf)

hadoop_application = load_yaml("applications/hadoop271.yml")
hadoop_application_id = genie.create_application(hadoop_application)
LOGGER.warn("Created Hadoop 2.7.1 application with id = [%s]" % hadoop_application_id)

spark_163_application = load_yaml("applications/spark163.yml")
spark_163_application_id = genie.create_application(spark_163_application)
LOGGER.warn("Created Spark 1.6.3 application with id = [%s]" % spark_163_application_id)

spark_201_application = load_yaml("applications/spark201.yml")
spark_201_application_id = genie.create_application(spark_201_application)
LOGGER.warn("Created Spark 2.0.1 application with id = [%s]" % spark_201_application_id)

hadoop_command = load_yaml("commands/hadoop271.yml")
hadoop_command_id = genie.create_command(hadoop_command)
LOGGER.warn("Created Hadoop command with id = [%s]" % hadoop_command_id)

hdfs_command = load_yaml("commands/hdfs271.yml")
hdfs_command_id = genie.create_command(hdfs_command)
LOGGER.warn("Created HDFS command with id = [%s]" % hdfs_command_id)

yarn_command = load_yaml("commands/yarn271.yml")
yarn_command_id = genie.create_command(yarn_command)
LOGGER.warn("Created Yarn command with id = [%s]" % yarn_command_id)

spark_163_shell_command = load_yaml("commands/sparkShell163.yml")
spark_163_shell_command_id = genie.create_command(spark_163_shell_command)
LOGGER.warn("Created Spark 1.6.3 Shell command with id = [%s]" % spark_163_shell_command_id)

spark_163_submit_command = load_yaml("commands/sparkSubmit163.yml")
spark_163_submit_command_id = genie.create_command(spark_163_submit_command)
LOGGER.warn("Created Spark 1.6.3 Submit command with id = [%s]" % spark_163_submit_command_id)

spark_201_shell_command = load_yaml("commands/sparkShell201.yml")
spark_201_shell_command_id = genie.create_command(spark_201_shell_command)
LOGGER.warn("Created Spark 2.0.1 Shell command with id = [%s]" % spark_201_shell_command_id)

spark_201_submit_command = load_yaml("commands/sparkSubmit201.yml")
spark_201_submit_command_id = genie.create_command(spark_201_submit_command)
LOGGER.warn("Created Spark 2.0.1 Submit command with id = [%s]" % spark_201_submit_command_id)

genie.set_application_for_command(hadoop_command_id, [hadoop_application_id])
LOGGER.warn("Set applications for Hadoop command to = [%s]" % hadoop_application_id)

genie.set_application_for_command(hdfs_command_id, [hadoop_application_id])
LOGGER.warn("Set applications for HDFS command to = [[%s]]" % hadoop_application_id)

genie.set_application_for_command(yarn_command_id, [hadoop_application_id])
LOGGER.warn("Set applications for Yarn command to = [[%s]]" % hadoop_application_id)

genie.set_application_for_command(spark_163_shell_command_id, [hadoop_application_id, spark_163_application_id])
LOGGER.warn("Set applications for Spark 1.6.3 Shell command to = [%s]" %
            [hadoop_application_id, spark_163_application_id])

genie.set_application_for_command(spark_163_submit_command_id, [hadoop_application_id, spark_163_application_id])
LOGGER.warn("Set applications for Spark 1.6.3 Submit command to = [%s]" %
            [hadoop_application_id, spark_163_application_id])

genie.set_application_for_command(spark_201_shell_command_id, [hadoop_application_id, spark_201_application_id])
LOGGER.warn("Set applications for Spark 2.0.1 Shell command to = [%s]" %
            [hadoop_application_id, spark_201_application_id])

genie.set_application_for_command(spark_201_submit_command_id, [hadoop_application_id, spark_201_application_id])
LOGGER.warn("Set applications for Spark 2.0.1 Submit command to = [%s]" %
            [hadoop_application_id, spark_201_application_id])

prod_cluster = load_yaml("clusters/prod.yml")
prod_cluster_id = genie.create_cluster(prod_cluster)
LOGGER.warn("Created prod cluster with id = [%s]" % prod_cluster_id)

test_cluster = load_yaml("clusters/test.yml")
test_cluster_id = genie.create_cluster(test_cluster)
LOGGER.warn("Created test cluster with id = [%s]" % test_cluster_id)

genie.set_commands_for_cluster(
    prod_cluster_id,
    [hadoop_command_id, hdfs_command_id, yarn_command_id, spark_163_shell_command_id, spark_201_shell_command_id,
     spark_163_submit_command_id, spark_201_submit_command_id]
)
LOGGER.warn("Added all commands to the prod cluster with id = [%s]" % prod_cluster_id)
genie.set_commands_for_cluster(
    test_cluster_id,
    [hadoop_command_id, hdfs_command_id, yarn_command_id, spark_163_shell_command_id, spark_201_shell_command_id,
     spark_163_submit_command_id, spark_201_submit_command_id]
)
LOGGER.warn("Added all commands to the test cluster with id = [%s]" % test_cluster_id)