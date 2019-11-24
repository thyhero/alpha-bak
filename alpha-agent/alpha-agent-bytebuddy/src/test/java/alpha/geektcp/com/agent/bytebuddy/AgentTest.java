package alpha.geektcp.com.agent.bytebuddy;

import alpha.geektcp.com.agent.bytebuddy.example.LoggerAdvisor;
import alpha.geektcp.com.agent.bytebuddy.example.Service;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.NamingStrategy;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.pool.TypePool;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author tanghaiyang on 2019/11/24 16:58.
 */
public class AgentTest {

    @Test
    public void ServiceTest() throws Exception{
        Service service = new ByteBuddy()
                .subclass(Service.class)
//                .method(ElementMatchers.any())
                .defineField("aaa", String.class)
                .defineField("bbb", boolean.class)
                .method(ElementMatchers.any())
                .intercept(Advice.to(LoggerAdvisor.class))
//                .method(ElementMatchers.any())
//                .intercept(Advice.to(LoggerAdvisor.class))
                .make()
                .load(Service.class.getClassLoader())
                .getLoaded()
                .newInstance();
        service.bar(123);
        service.foo(456);
    }

    @Test
    public void buyteBuddyTest(){
        Class<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.named("toString"))
                .intercept(FixedValue.value("Hello World!"))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded();



    }

    @Test
    public void serviceTest() {
//        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
//                .subclass(Object.class)
//                .name("example.Type")
//                .make();

//        DynamicType.Unloaded<?> dynamicType2 = new ByteBuddy()
//                .with(new NamingStrategy.AbstractBase() {
//                    @Override
//                    public String subclass(TypeDescription superClass) {
//                        return "i.love.ByteBuddy." + superClass.getSimpleName();
//                    }
//                })
//                .subclass(Object.class)
//                .make();
//
        ByteBuddy byteBuddy = new ByteBuddy();
        byteBuddy.with(new NamingStrategy.SuffixingRandom("suffix"));
        DynamicType.Unloaded<?> dynamicType = byteBuddy.subclass(Object.class).make();
    }


    @Test
    public void generateClass(){
//        DynamicType.Unloaded<?> dynamicType = new ByteBuddy()
//                .subclass(Object.class)
//                .make();
//
//        DynamicType.Unloaded<?> dynamicType2 = new ByteBuddy()
//                .subclass(Object.class)
//                .name("example.Type")
//                .make();
//
//        DynamicType.Unloaded<?> dynamicType3 = new ByteBuddy()
//                .with(new NamingStrategy.AbstractBase() {
//                    @Override
//                    public String subclass(TypeDescription superClass) {
//                        return "i.love.ByteBuddy." + superClass.getSimpleName();
//                    }
//                })
//                .subclass(Object.class)
//                .make();

        ByteBuddy byteBuddy = new ByteBuddy();
        byteBuddy.with(new NamingStrategy.SuffixingRandom("suffix"));
        DynamicType.Unloaded<?> dynamicType = byteBuddy.subclass(Object.class).make();
    }


    class Foo {
        String m() { return "foo"; }
        String bar() { return "bar"; }
    }

    class Bar {
        String qux;
        String m() { return "bar"; }
    }

    public static void main(String[] args) {
//        TypePool typePool = TypePool.Default.ofBootLoader();
//        new ByteBuddy()
//                .redefine(typePool.describe("foo.Bar").resolve(), // do not use 'Bar.class'
//                        ClassFileLocator.ForClassLoader.ofBootLoader())
//                .defineField("qux", String.class) // we learn more about defining fields later
//                .make()
//                .load(ClassLoader.getSystemClassLoader());
//        assertThat(Bar.class.getDeclaredField("qux"), notNullValue());
    }

    @Test
    public void test111(){
//        ByteBuddyAgent.install();
//        Foo foo = new Foo();
//        new ByteBuddy()
//                .redefine(Bar.class)
//                .name(Foo.class.getName())
//                .make()
//                .load(Foo.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
//        assertThat(foo.m(), is("bar"));
    }
}
