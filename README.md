# Azure SAS Token Provider for Hadoop
## Summary
This project provides a custom implementation of SASTokenProvider interface provided by Hadoop. This custom class implementation is required to enable SAS token access to Azure Data Lake Storage (ADLS). Using the jar created from this custom class, we can enable token based remote reads from ADLS directly using AWS services like [Amazon EMR](https://aws.amazon.com/emr/), [AWS Glue](https://aws.amazon.com/glue/) etc.
## Description
Open source Hadoop include modules like [hadoop-aws](https://hadoop.apache.org/docs/stable/hadoop-aws/tools/hadoop-aws/index.html) and [hadoop-azure](https://hadoop.apache.org/docs/stable/hadoop-azure/abfs.html) to provide support for AWS and Azure integration respectively. Azure provides various options to authorize and authenticate requests to storage. And [shared access signature (SAS)](https://learn.microsoft.com/en-us/rest/api/storageservices/delegate-access-with-shared-access-signature) is one such option. With SAS you can grant restricted access to ADLS resources over a specified time interval.

AWS services like [Amazon EMR](https://aws.amazon.com/emr/) and [AWS Glue](https://aws.amazon.com/glue/), use Apache Hadoop, Apache Spark and other open-source modules. Out of the box, currently Amazon EMR or AWS Glue cannot connect to ADLS directly using SAS tokens. There are different methods in which you can connect AWS services that use Hadoop and Spark to ADLS, but all those require custom configurations. 

For example, the SAS based access to ADLS is possible in Amazon EMR version 6.9.0 and above which bundle hadoop-common 3.3.0 where support for [HADOOP-16730](https://issues.apache.org/jira/browse/HADOOP-16730) has been implemented. However, the hadoop-azure module provides only a "SASTokenProvider" interface that is not yet implemented as a class. For accessing ADLS using SAS tokens, this interface should be implemented as a custom class and presented as a configuration in AWS services that use open source Hadoop libraries like Amazon EMR and AWS Glue.

In this project we provide a custom class that implements the "SASTokenProvider" interface that is required to enable the SAS token based access to ADLS using Hadoop and Spark. 
## Security

See [CONTRIBUTING](CONTRIBUTING.md#security-issue-notifications) for more information.

## License

This library is licensed under the MIT-0 License. See the LICENSE file.

