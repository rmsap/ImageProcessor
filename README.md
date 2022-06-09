# ImageProcessor
Program overview:
The ImageProcessor represents a fully-functioning image manipulation application written in Java. Currently, it only supports a text-based user interface but will 
eventually support a graphical user interface. The design is split up into a model, view, and controller, each of which is in its own package with an appropriate name.
The ImageProcessorController interface contains the method that would be needed for a controller implementation. The ImageProcessorControllerImpl class represents an
implementation of that interface with its one method defined to enable the text-based version of the ImageProcessor to function. The controller implementation makes use
of the ImageFormat interface, which defines the method needed to read and write from any file type. The PPMImageFormat is an implementation of that interface which
enables reading and writing from ppm files. In the future, additional classes can be added (such as PNGImageFormat) to read from additional file types. Also, the 
controller implementation has an ImageProcessorView and an ImageProcessorModel which it uses to execute the application. The ImageProcessorView defines methods that
are needed for any user interface for the ImageProcessor program (text or GUI based). There is an abstract class, AbstractImageProcessorView, that implements this
interface so that in the future we can use it to prevent code duplication since it is likely that all views will have the same renderMessage method. Finally, there is
ImageProcessorTextView, the concrete implementation of AbstractImageProcessorView which represents enables the text-based user interface to be displayed. In terms
of the model, there is first the ImageProcessorViewModel which contains the getImage method. The ImageProcessorModel interface, which contains the rest of the model's
methods, extends the ImageProcessorViewModel interface so that the view (which has a ImageProcessorViewModel) has access to less of the model's methods. Then, there is
the implementation of the ImageProcessorModel, ImageProcessorModelImpl, which concretely defines everything that a model needs to be able to do (load an image,
execute an operation on it, and get an image, which is used by the operations). Finally, we have the operations, which are an implementation of the command design
pattern. The Operation interface represents a function object which has a method that allows it to operate on an image, and each implementation of this interface 
represents a different operation. For example, BrightenOrDarken is an operation that would brighten an image or darken an image (based on the input to the function),
FlipHorizontal horizontally flips the image, FlipVertical vertically flips the image, and Greyscale greyscales the image based on the given factor (currently the only
supported greyscale factors are color: red, green, or blue, or brightness: luma, intensity, or value).

