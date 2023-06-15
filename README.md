# Azure SAS Token Provider for Hadoop

## Summary
This project provides a custom implementation of SASTokenProvider interface provided by Hadoop. This custom class implementation is required to enable SAS token access to Azure Data Lake Storage (ADLS). Using the jar created from this custom class, we can enable token based remote reads from ADLS directly using AWS services like [Amazon EMR](https://aws.amazon.com/emr/), [AWS Glue](https://aws.amazon.com/glue/) etc.

Using the custom class, blog post [Enable remote reads from Azure ADLS with SAS tokens using Spark in Amazon EMR](https://aws.amazon.com/blogs/big-data/enable-remote-reads-from-azure-adls-with-sas-tokens-using-spark-in-amazon-emr/) demonstrates how to set up authentication and authorization to remote data sources in Azure Data Lake Storage (ADLS) using a shared access signature (SAS) when running Apache Spark jobs via EMR Notebooks attached to an EMR cluster.

## Description
Open source Hadoop include modules like [hadoop-aws](https://hadoop.apache.org/docs/stable/hadoop-aws/tools/hadoop-aws/index.html) and [hadoop-azure](https://hadoop.apache.org/docs/stable/hadoop-azure/abfs.html) to provide support for AWS and Azure integration respectively. Azure provides various options to authorize and authenticate requests to storage. And [shared access signature (SAS)](https://learn.microsoft.com/en-us/rest/api/storageservices/delegate-access-with-shared-access-signature) is one such option. With SAS you can grant restricted access to ADLS resources over a specified time interval.

AWS services like [Amazon EMR](https://aws.amazon.com/emr/) and [AWS Glue](https://aws.amazon.com/glue/), use Apache Hadoop, Apache Spark and other open-source modules. Out of the box, currently Amazon EMR or AWS Glue cannot connect to ADLS directly using SAS tokens. There are different methods in which you can connect AWS services that use Hadoop and Spark to ADLS, but all those require custom configurations. 

For example, the SAS based access to ADLS is possible in Amazon EMR version 6.9.0 and above which bundle hadoop-common 3.3.0 where support for [HADOOP-16730](https://issues.apache.org/jira/browse/HADOOP-16730) has been implemented. However, the hadoop-azure module provides only a "SASTokenProvider" interface that is not yet implemented as a class. For accessing ADLS using SAS tokens, this interface should be implemented as a custom class and presented as a configuration in AWS services that use open source Hadoop libraries like Amazon EMR and AWS Glue.

In this project we provide a custom class that implements the "SASTokenProvider" interface that is required to enable the SAS token based access to ADLS using Hadoop and Spark. 

**Note-** *Don't expose the SAS token in the configuration files. It is recommended to save the SAS token in AWS Secrets Manager and set the value to `fs.azure.sas.fixed.token.<azure storage account>.dfs.core.windows.net` at runtime.*

## Compilation and Packaging Details
The details of all the dependencies used for the first version developed can be found in the provided pom.xml file within the project. Some of the important details are listed below. 
* Java version used to compile, package and test the first version - openjdk version "1.8.0_362"
* Dependent open-source modules used to compile, package and test the first version:
    * hadoop-azure-3.3.3
    * hadoop-common-3.3.3
    * hadoop-client-3.3.3

## Usage Patterns and Testing Details
The jar file created created from this custom class implementation can be used to access data in ADLS using SAS tokens from AWS services. 
* AWS Services tested for ADLS data access using SAS tokens using the jar created from the first version:
    * Amazon EMR 6.9.0
    * AWS Glue 4.0 (See Note below)

**Note-** *For AWS Glue, you have to include the dependencies (hadoop-azure-3.3.3.jar, hadoop-common-3.3.3.jar) while creating the jar file or provide those as additional jars in "Dependent JARs path" property.*

## How to Create Package
1. Download and install [Maven](https://maven.apache.org/index.html)
2. Clone this repo
3. Execute the following steps from command line:
    * Change working directory to the project's root directory
        * `cd azsastknprovider`
    * Package using mvn
        * `mvn clean package`
4. The jar file would be present in the `target` directory
5. Copy this jar and present it as needed by the AWS service being used
    * For Amazon EMR add this jar in the HADOOP_CLASSPATH and SPARK_DIST_CLASSPATH
    * For AWS Glue this jar should be included in "Dependent JARs path" property

**Note-1** 
*The above steps create a simple jar without including any dependencies. If you need to create an uber jar with all the dependencies included or a shaded uber jar with dependencies relocated, follow the instructions from Maven to update the pom.xml file as needed and then package it.*

**Note-2**
*If you need to create the jar file for subsequent releases, please update the pom.xml file to update any necessary dependencies*

## External References
* https://hadoop.apache.org/docs/stable/hadoop-azure/abfs.html
* https://learn.microsoft.com/en-us/azure/storage/common/storage-sas-overview
* https://learn.microsoft.com/en-us/azure/databricks/storage/azure-storage

## Security
See [CONTRIBUTING](CONTRIBUTING.md#security-issue-notifications) for more information.

## License
This library is licensed under the MIT-0 License. See the [LICENSE](LICENSE) file.
