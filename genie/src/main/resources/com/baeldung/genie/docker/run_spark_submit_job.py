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
                                                                                      
##################################################################################    
# This script assumes setup.py has already been run to configure Genie and that       
# this script is executed on the host where Genie is running. If it's executed on     
# another host change the localhost line below.                                       
##################################################################################    
                                                                                      
from __future__ import absolute_import, division, print_function, unicode_literals    
                                                                                      
import logging                                                                        
import sys                                                                            
                                                                                      
import pygenie                                                                        
                                                                                      
logging.basicConfig(level=logging.ERROR)                                              
                                                                                      
LOGGER = logging.getLogger(__name__)                                                  
                                                                                      
pygenie.conf.DEFAULT_GENIE_URL = "http://genie:8080"                                  
                                                                                      
# Create a job instance and fill in the required parameters                           
job = pygenie.jobs.GenieJob() \                                                       
    .genie_username('root') \                                                         
    .job_version('3.0.0')                                                             
                                                                                      
# Set cluster criteria which determine the cluster to run the job on                  
job.cluster_tags(['sched:' + str(sys.argv[1]), 'type:yarn'])                          
                                                                                      
# Set command criteria which will determine what command Genie executes for the job   
if len(sys.argv) == 2:                                                                
    # Use the default spark                                                           
    job.command_tags(['type:spark-submit'])                                           
    job.job_name('Genie Demo Spark Submit Job')                                       
else:                                                                                 
    # Use the spark version passed in                                                 
    job.command_tags(['type:spark-submit', 'ver:' + str(sys.argv[2])])                
    job.job_name('Genie Demo Spark ' + str(sys.argv[2]) + ' Submit Job')              
                                                                                      
# Any command line arguments to run along with the command. In this case it holds     
# the actual query but this could also be done via an attachment or file dependency.  
# This jar location is where it is installed on the Genie node but could also pass    
# the jar as attachment and use it locally                                            
if len(sys.argv) == 2:                                                                
    # Default is spark 1.6.3                                                          
    job.command_arguments(                                                            
        "--class org.apache.spark.examples.SparkPi "                                  
        "${SPARK_HOME}/lib/spark-examples*.jar "                                      
        "10"                                                                          
    )                                                                                 
else:                                                                                 
    # Override with Spark 2.x location                                                
    job.command_arguments(                                                            
        "--class org.apache.spark.examples.SparkPi "                                  
        "${SPARK_HOME}/examples/jars/spark-examples*.jar "                            
        "10"                                                                          
    )                                                                                 
                                                                                      
# Submit the job to Genie                                                             
running_job = job.execute()                                                           
                                                                                      
print('Job {} is {}'.format(running_job.job_id, running_job.status))                  
print(running_job.job_link)                                                           
                                                                                      
# Block and wait until job is done                                                    
running_job.wait()                                                                    
                                                                                      
print('Job {} finished with status {}'.format(running_job.job_id, running_job.status))