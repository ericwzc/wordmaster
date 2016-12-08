import xml.etree.ElementTree as ET

def tr2Gradle():
    s = """
      <dependencies>
        <dependency>
            <groupId>org.free</groupId>
            <artifactId>common</artifactId>
        </dependency>
        <dependency>
            <groupId>com.google.inject</groupId>
            <artifactId>guice</artifactId>
        </dependency>
        <dependency>
            <groupId>javazoom</groupId>
            <artifactId>jlayer</artifactId>
        </dependency>

        <dependency>
            <groupId>com.jgoodie</groupId>
            <artifactId>beansbinding</artifactId>
        </dependency>

        <dependency>
            <groupId>com.jgoodie</groupId>
            <artifactId>jfd-annotations</artifactId>
        </dependency>

        <dependency>
            <groupId>com.jgoodie</groupId>
            <artifactId>jfd-loader</artifactId>
        </dependency>

        <dependency>
            <groupId>com.jgoodie</groupId>
            <artifactId>jgoodies-common</artifactId>
        </dependency>

        <dependency>
            <groupId>com.jgoodie</groupId>
            <artifactId>jgoodies-forms</artifactId>
        </dependency>

        <dependency>
            <groupId>com.jgoodie</groupId>
            <artifactId>jgoodies-uif-lite</artifactId>
        </dependency>

        <dependency>
            <groupId>com.jgoodie</groupId>
            <artifactId>swing-layout</artifactId>
        </dependency>

        <dependency>
            <groupId>com.jgoodie</groupId>
            <artifactId>TableLayout</artifactId>
        </dependency>

        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-simple</artifactId>
        </dependency>
    </dependencies>
    """

    s = """
    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>3.8.1</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.free</groupId>
            <artifactId>common</artifactId>
        </dependency>

        <dependency>
            <groupId>com.google.inject</groupId>
            <artifactId>guice</artifactId>
        </dependency>

        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-core</artifactId>
        </dependency>

        <dependency>
            <groupId>net.bytebuddy</groupId>
            <artifactId>byte-buddy</artifactId>
        </dependency>

        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-simple</artifactId>
        </dependency>

    </dependencies>
    """
    # tree = ET.parse('D:\wordmaster\client\pom.xml')
    # root = tree.getroot()
    root = ET.fromstring(s)
    deps = root.findall("./dependency")
    for dep in deps:
        g = dep.find('./groupId')
        a = dep.find('./artifactId')
        print("compile group: '{}', name: '{}', version: '{}'".format(g.text, a.text, '1.0'))


if __name__ == '__main__':
    tr2Gradle()
