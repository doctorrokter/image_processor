# Image Processor
Online image processor, currently supports <strong>ONLY</strong> blurring, resizing on the fly.


<strong>Requirements</strong>:
* ImageMagick should be installed on the machine. 
* Also, if this is a Windows machine, a MAGICK_HOME environment variable should be set in the system.

Gaussian Blur template:

<pre>
    http://localhost:8080/blur/${radius}/${url}
</pre>

Where:
* ${radius} - radius for Gaussian Blur.
* ${url} - URL to a remote image.

Example:

<pre>
    http://localhost:8080/blur/65/retrowave.ru/artwork/c99d5d4a4951c230ded9d9ffec2c757f85593814.jpg
</pre>

Resize template:

<pre>
    http://localhost:8080/resize/${width}/${height}/${url}
</pre>

Where:
* ${width} - desired width of an image.
* ${height} - desired height of an image.
* ${url} - URL to a remote image.

Example:

<pre>
    http://localhost:8080/resize/250/250/retrowave.ru/artwork/c99d5d4a4951c230ded9d9ffec2c757f85593814.jpg
</pre>
