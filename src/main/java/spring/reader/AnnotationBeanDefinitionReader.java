package spring.reader;

import service.HelloServiceImpl;
import spring.aspect.ResourceAspect;
import spring.aspect.ServiceAspect;
import spring.bean.BeanDefinition;
import spring.bean.BeanReference;
import spring.bean.PropertyValue;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * （描述）
 *
 * @author tangzw
 * @date 2019-02-26
 * @since (版本)
 */
public class AnnotationBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public AnnotationBeanDefinitionReader(String location) {
        super(location);
    }

    @Override
    public void loadBeanDefinitions() {
        String location = getLocation();
        doLoadBeanDefinitions(location);
    }

    protected void doLoadBeanDefinitions(String location){
        System.out.println(location);
        File file = new File(getLocation());

        System.out.println(file.exists());
        // 如果不是文件夹
        if (!file.isDirectory()){
            return;
        }

        File[] files = file.listFiles();
        List<String> pathList = new ArrayList<>();

        for (File f:files){
            String absolutePath = f.getAbsolutePath();
            String startStr = "classes\\";
            String endStr = ".class";
            int startIndex = absolutePath.indexOf(startStr)+startStr.length();
            int endIndex = absolutePath.indexOf(endStr);

            String path = absolutePath.substring(startIndex,endIndex);
            String classPath = path.replace('\\','.');

            pathList.add(classPath);
        }

        System.out.println(pathList);

        registerBeanDefinition(pathList);
    }

    protected void registerBeanDefinition(List<String> pathList){
        for (String classPath:pathList){
            try {
                Class clazz = Class.forName(classPath);
                System.out.println(clazz.getName()+" "+clazz.isAnnotationPresent(ServiceAspect.class));

                if (clazz.isAnnotationPresent(ServiceAspect.class)){
                    BeanDefinition beanDefinition = new BeanDefinition();

                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field:fields){
                        if (field.isAnnotationPresent(ResourceAspect.class)){
                            BeanReference beanReference = new BeanReference();

                            String name = field.getName();
                            String value = field.getType().getName();

                            beanReference.setName(value);

                            beanDefinition.getPropertyValueList().add(new PropertyValue(name,beanReference));
                        }
                    }

                    beanDefinition.setBeanClass(classPath);

                    getRegister().put(clazz.getSimpleName(),beanDefinition);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println(getRegister().toString());
    }

    public static void main(String[] args) throws Exception {
        String str = "service";

        BeanDefinitionReader beanDefinitionReader  = new AnnotationBeanDefinitionReader(str);
        beanDefinitionReader.loadBeanDefinitions();

        Class clazz = HelloServiceImpl.class;
        System.out.println(clazz.getDeclaredFields().length);

    }
}
