package at.altin.bikemedcommons.annotation;

import java.lang.annotation.*;

/**
 * Marks the annotated code as experimental.
 *
 * <p>
 * Experimental code is intended for testing and evaluation purposes. It may undergo significant
 * changes or be removed in future versions without notice. Use it at your own risk.
 * </p>
 *
 * <p>
 * Example:
 * </p>
 *
 * <pre>
 * &#064;Experimental
 * public class ExperimentalClass {
 *     // Experimental code here
 * }
 * </pre>
 *
 * @author Altin
 */

@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE })
public @interface Experimental {
    // empty
}
