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

Running provided operation script:
Open res/commandScript.txt. When the program is run, copy and paste the file contents into the text-based user interface and press enter. The script will first load the koala image, then vertically flip the image, then save the vertically flipped image as koala-vertical.ppm, then greyscale the vertically flipped image by red, then brighten the original koala image, then save the red greyscaled image as koalaVertFlipRed.ppm, save the brightened koala image as koalaBright.ppm, and finally quit the program. If you would like the program to keep running after executing the script, delete the 'q' character from the end of the line before pressing enter.

Image Citation:
The 4x3ppmOriginal.ppm and 4x4ppmOriginal.ppm files were created and owned by the programmers of this application. They both authorize its use in this program. All variations of these images were created by this program and thus are owned by its developers, and they authorize their use as well. Note: the provided Koala.ppm image from the starter code is used in the folder solely for testing purposes and is not meant to be an example image.
The Warner.jpg, sampleBMP.bmp, and neil.png were created and owned by the programmers of this application. They both authorize its use in this program. All variations of these images were created by this program and thus are owned by its developers, and they authorize their use as well.

Changes between Assignments 4 and 5:
Greyscale class was renamed to VisualizeComponent. This was done because the "greyscale" command that was added is different from what this pre-existing code did, and the class having the same name as a different command would be confusing. So, it was renamed to avoid this confusion.
The "sepia", "greyscale", "sharpen", and "blur" operations were added to the operationDirectory Map in the controller. This had to be done because it is the best way to add new operations while having to modify as little code as possible (since the controller needs to know what input to look for). Tests were also added to the OperationTest class to test that these operations work properly.
In ImageProcessorControllerImpl, new script commands ("sepia", "greyscale", "blur", and "sharpen") were added to the operationDirectory Map as keys with the appropriate Operation function object as their associated value. These commands were also added to the menu of commands that appears when the program starts.
In the BrightenOrDarken class, the inner for loop now counts until j < 3, not j < deepCopy[i].length, because the original implementation would not work for png images which contain alpha values (it would brighten/darken the alpha value which it should not do).
In ImageProcessorControllerImpl, new script commands ("png", "bmp", and "jpg") were added to the formatDirectory Map as keys with the appropriate ImageFormat as their associated value.
