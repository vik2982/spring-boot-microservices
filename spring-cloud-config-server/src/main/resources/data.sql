-- Mock server config
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, PROP_KEY, PROP_VAL)
values('books-api', 'default', '1.1', 'mock.server.port', '1080');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, PROP_KEY, PROP_VAL)
values('books-api', 'default', '1.1', 'mock.server.path', '/books-mock-api');

-- Resilience4J config
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, PROP_KEY, PROP_VAL)
values('books-api', 'default', '1.1', 'resilience4j.retry.instances.mockServiceCallRetry.maxAttempts', '3');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, PROP_KEY, PROP_VAL)
values('books-api', 'default', '1.1', 'resilience4j.retry.instances.mockServiceCallRetry.waitDuration', '3000');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, PROP_KEY, PROP_VAL)
values('books-api', 'default', '1.1', 'resilience4j.retry.instances.mockServiceCallRetry.retryExceptionPredicate', 'com.va.books.client.RetryPredicate');

-- Spring security config
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, PROP_KEY, PROP_VAL)
values('books-api', 'default', '1.1', 'security.enabled', 'false');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, PROP_KEY, PROP_VAL)
values('books-api', 'default', '1.1', 'security.jwt.secret-key', '3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, PROP_KEY, PROP_VAL)
values('books-api', 'default', '1.1', 'security.jwt.expiration-time', '3600000');


-- Profile related config
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, PROP_KEY, PROP_VAL)
values('books-api', 'local', '1.1', 'mock.server.host', 'localhost');
INSERT INTO PROPERTIES (APPLICATION, PROFILE, LABEL, PROP_KEY, PROP_VAL)
values('books-api', 'dev', '1.1', 'mock.server.host', 'mock-server');



