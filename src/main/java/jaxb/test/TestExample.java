package jaxb.test;

import jaxb.model.Company;
import jaxb.model.Department;
import jaxb.model.Employee;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class TestExample {

    private static final String XML_FILE = "src/main/resources/dept-info.xml";
    //private static final String XML_FILE = "G:\Study IBA\PrilIS\lab3_parsers_JAXB\src\main\resources\dept-info.xml";

    public static void main(String[] args) {
        //department D01
        Employee empD01 = new Employee("E01", "Tom", null);
        Employee emp2D01 = new Employee("E02", "Mary", "E01");
        Employee emp3D01 = new Employee("E03", "John", null);

        List<Employee> empListD01 = new ArrayList<Employee>();
        empListD01.add(empD01);
        empListD01.add(emp2D01);
        empListD01.add(emp3D01);

        Department dept01 = new Department("D01", "ACCOUNTING", "NEW YORK");
        dept01.setEmployees(empListD01);

        List<Department> deptList = new ArrayList<Department>();
        deptList.add(dept01);

        //department D02
        Employee empD02 = new Employee("P01", "Bob", null);
        Employee emp2D02 = new Employee("P02", "Zig", null);
        Employee emp3D02 = new Employee("P03", "Rob", null);

        List<Employee> empListD02 = new ArrayList<Employee>();
        empListD01.add(empD02);
        empListD01.add(emp2D02);
        empListD01.add(emp3D02);

        Department dept02 = new Department("D02", "SALES", "LA");
        dept02.setEmployees(empListD02);

        deptList.add(dept02);

        //company
        Company company = new Company("JB", deptList);

        try {
            // create JAXB context and instantiate marshaller
            JAXBContext context = JAXBContext.newInstance(Company.class);

            // (1) Marshaller : Java Object to XML content.
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            m.marshal(company, System.out);

            // Write to File
            File outFile = new File(XML_FILE);
            m.marshal(company, outFile);

            System.err.println("Write to file: " + outFile.getAbsolutePath());
            // (2) Unmarshaller : Read XML content to Java Object.
            Unmarshaller um = context.createUnmarshaller();
            // XML file create before.


            Company compFromFile1 = (Company) um.unmarshal(new FileReader(
                    XML_FILE));
            String compName = compFromFile1.getCompName();
            System.out.println("Company name: " + compName);
            List<Department> deps = compFromFile1.getDepartments();
            for (Department department : deps) {
                System.out.printf("DepN: %s, depName: %s, depLocation: %s", department.getDeptNo(), department.getDeptName(), department.getLocation());
                for (Employee emp : department.getEmployees()) {
                    System.out.println("Employee: " + emp.getEmpName());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}