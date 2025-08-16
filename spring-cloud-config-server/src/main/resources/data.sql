INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, PROP_KEY, PROP_VAL)
values('books-api', 'default', '1.1', 'port', '1080');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, PROP_KEY, PROP_VAL)
values('books-api', 'default', '1.1', 'mock-path', '/books-mock-api');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, PROP_KEY, PROP_VAL)
values('books-api', 'default', '1.1', 'resilience4j.retry.instances.mockServiceCallRetry.maxAttempts', '3');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, PROP_KEY, PROP_VAL)
values('books-api', 'default', '1.1', 'resilience4j.retry.instances.mockServiceCallRetry.waitDuration', '3000');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, PROP_KEY, PROP_VAL)
values('books-api', 'default', '1.1', 'resilience4j.retry.instances.mockServiceCallRetry.retryExceptionPredicate', 'com.va.books.client.RetryPredicate');

INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, PROP_KEY, PROP_VAL)
values('books-api', 'local', '1.1', 'host', 'localhost');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, PROP_KEY, PROP_VAL)
values('books-api', 'dev', '1.1', 'host', 'mock-server');



