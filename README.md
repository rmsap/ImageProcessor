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

Completed parts of program:
The Features interface and FeaturesImpl interface are completed. If additional features are to be added in the future, an interface extending the current Features interface should be created and an implementation extending the current implementation should be created. All ImageFormats currently created (JPG, PNG, BMP, PPM) are complete and additional ImageFormats can be added by making new classes that implement the ImageFormat interface. The model implementation and interfaces are complete and should not need to be modified. All pre-existing Operations besides ColorTransformation and Filter are completed. ColorTransformation and Filter could have additional transformations/filters respectively added. Additional Operations can be added by creating new classes that implement the Operations interface. The Both view implementations (the GUI view and the text view) could be modified in the future if additional operations are added (the text view would need to display the new Operation in its menu and the GUI view would need a button for the operation).

Image Citation:
The 4x3ppmOriginal.ppm and 4x4ppmOriginal.ppm files were created and owned by the programmers of this application. They both authorize its use in this program. All variations of these images were created by this program and thus are owned by its developers, and they authorize their use as well. Note: the provided Koala.ppm image from the starter code is used in the folder solely for testing purposes and is not meant to be an example image.
The Warner.jpg and neil.png were created and owned by the programmers of this application. They both authorize its use in this program. All variations of these images were created by this program and thus are owned by its developers, and they authorize their use as well.
The sampleBMP.bmp image came from https://filesamples.com/formats/bmp and it is a free file sample authorized for private use.
The p2.jpg was created and owned by the programmers of this application. They both authorize its use in this program. All variations of these images were created bt this program and thus are owned by its developers, and they authorize their use as well.

Changes to implement Downsize Operation:
The only changes to the program to implement Downsize were creating an additional button in the GUI-based view and adding a method for it to the Features interface. No previously written code needed to be changed. Additionally, a Downsize class that implements the Operation class was created in order to actually code the logic for Downsize. Also note that tests for the Downsize operation were added to the OperationTest class, and tests for its implementation in the Features interface were added to the FeaturesTest class. The two images that display downsizing are downsized versions of p2.jpg. The first is p230.png, which had both its height and width downsized to 30% of the original. The second is p22010.jpg. which had its width downsized to 20% of the original and its height downsized to 10% of the original.

Changes between Assignments 4 and 5:
Greyscale class was renamed to VisualizeComponent. This was done because the "greyscale" command that was added is different from what this pre-existing code did, and the class having the same name as a different command would be confusing. So, it was renamed to avoid this confusion.
The "sepia", "greyscale", "sharpen", and "blur" operations were added to the operationDirectory Map in the controller. This had to be done because it is the best way to add new operations while having to modify as little code as possible (since the controller needs to know what input to look for). Tests were also added to the OperationTest class to test that these operations work properly.
In ImageProcessorControllerImpl, new script commands ("sepia", "greyscale", "blur", and "sharpen") were added to the operationDirectory Map as keys with the appropriate Operation function object as their associated value. These commands were also added to the menu of commands that appears when the program starts.
In the BrightenOrDarken class, the inner for loop now counts until j < 3, not j < deepCopy[i].length, because the original implementation would not work for png images which contain alpha values (it would brighten/darken the alpha value which it should not do).
In ImageProcessorControllerImpl, new file extensions ("png", "bmp", and "jpg") were added to the formatDirectory Map as keys with the appropriate ImageFormat as their associated value. Additionally, added functionality to the controller to account for non-supported filetypes, because if we attempted to save or load an image without an extension, then the controller would throw a NullPointerException.

Changes between Assignment 5 and 6:
A new controller interface ImageProcessorGUIController was created to support a controller that dealt with a GUI-based view. The interface extends the original ImageProcessorGUIController interface. A new interface was needed because the Features interface, which house the callback functions, tell the controller how to manipulate the model, which the controller then relays to the model.
A new controller was created that implements the new interface meant for GUI-based views. Unlike the original controller, the new controller composes a Features object in to receive requests from the Feature and then tell the model to act on such requests.
A new abstract class for controllers, AbstractImageProcessorController, was created in order reduce code duplication in the two controller implementations. It implements ImageProcessorController but defines its only method (execute) abstractly. It contains fields common to both controllers (the format directory, the model, and the view) and instantiates them in its constructor. The program was refactored in this way because the fields would have been duplicated across both controllers, and this removed that duplication.
A new view interface ImageProcessorGUIView was created in order to support a GUI-based view that operated properly.
A new view, GUIViewImpl, that implements the new ImageProcessorGUIView interface was created. The class is a concrete implementation of the GUI that is used and supported in the program.
In AbstractModernImageFormat, the helper method createBufferedImage(int [][] image) was abstracted to a utility interface and implementation called Utils (interface) and UtilsImpl (interface implementation). This design decision was made because the code from the helper method was also used in the ImageProcessorControllerGUI helper method produceBufferedImage(String). We didn't want the controller to have to call on an ImageFormat in order to use the method. Now, both the GUI controller and the protected helper method of AbstractModernImageFormat call on the Util's createBufferedImage(int[][]) method to do the same thing as before.


In the res folder, ProgramWithImage.png contains the screenshot of the program that has a loaded image in it.
In the res folder, p2.jpg is the image that is loaded in the program that is shown in the screenshot.
