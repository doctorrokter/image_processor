# Image Processor
Online image processor, currently supports <strong>ONLY</strong> blurring on the fly.

Gaussian Blur template:

<pre>
    http://localhost:8080/blur/${radius}/${url}
</pre>

Where:
* ${radius} -  is a radius for Gaussian Blur.
* ${url} - is an URL to a remote image

Example:

<pre>
    http://localhost:8080/blur/65/retrowave.ru/artwork/c99d5d4a4951c230ded9d9ffec2c757f85593814.jpg
</pre>
