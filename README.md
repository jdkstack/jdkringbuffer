![Language](https://img.shields.io/badge/language-java-orange.svg)
![JDK](https://img.shields.io/badge/OpenJDK-11-yellow.svg)
![Maven](https://raster.shields.io/badge/Maven-3.6.3-red.svg)
![License](https://img.shields.io/badge/license-GPL2.0-000000.svg)

![google-java-format](https://img.shields.io/badge/google-javaformat-red.svg)
![tool-checkstyle](https://img.shields.io/badge/(google/sun)-checkstyle-orange.svg)
![editor-code-style](https://img.shields.io/badge/(google/sun)-codestyle-yellow.svg)
![editor-inspections](https://img.shields.io/badge/idea-inspections-red.svg)

![sonar-check](https://img.shields.io/badge/sonar-check-yellow.svg)
![ali-check](https://img.shields.io/badge/ali-check-red.svg)
![no-cyclic-dependency](https://img.shields.io/badge/cyclic-dependency-red.svg)
![annotation-rate](https://img.shields.io/badge/annotation-rate-red.svg)
![(api/core)-lib](https://img.shields.io/badge/(api/core)-lib-red.svg)

Benchmark:

One thread-->

JdkRingBufferBlockingQueue(31797526.910  ops/s)

ArrayBlockingQueue(27984910.583  ops/s)

DisruptorBlockingQueue(32313551.469  ops/s)

JdkRingBufferBlockingQueueV2(35082631.915  ops/s)

MPMCBlockingQueue(31987347.310   ops/s)

Five thread-->

JdkRingBufferBlockingQueue(30474493.910  ops/s)

ArrayBlockingQueue(18186582.583  ops/s)

DisruptorBlockingQueue(14835646.469  ops/s)

JdkRingBufferBlockingQueueV2(32580937.915  ops/s)

MPMCBlockingQueue(5474896.310   ops/s)


Ten thread-->

JdkRingBufferBlockingQueue(31825419.910  ops/s)

ArrayBlockingQueue(13424328.583  ops/s)

DisruptorBlockingQueue(9902521.469  ops/s)

JdkRingBufferBlockingQueueV2(32237347.915  ops/s)

MPMCBlockingQueue(3199302.310   ops/s)
