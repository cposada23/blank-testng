echo "Running Tests"
eval rm -rf target
eval mvn test \
    -Dtestng.thread.affinity=true \
    -DsuiteFile=src/test/resources/suites/hipertextualTestSuite.xml
