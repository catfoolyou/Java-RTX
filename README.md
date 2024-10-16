# Java-RTX
Basic java raytracer based on the Raytracing in one weekend series and some other code I stole off the internet. 

I have yet to make a proper sceneloader so deal with this hardcoded shit for now, hopefully I will make a proper sceneloader and API for this project at some point.

## API
Define a new scene like this:

`HittableList world = new HittableList();`
_____________________________________

Define materials like this
```java
Material green = new Lambertian(new Vector3(0.12, 0.45, 0.15));
Material metal = new Metal(new Vector3(0.7), 0.01);
Material light = new DiffuseLight(new Vector3(15));
```
For all material types look in `Material.java`

Lamberitans can be assigned textures, like this
```java
Material checker = new Lambertian(new Checker(0.32, new Vector3(0.2, 0.3, 0.1), new Vector3(0.9)));
```
_____________________________________

Add objects (anything that extends Hittable) using `world.add` like this
```java
world.add(new Quad(new Vector3(555,0,0), new Vector3(0,555,0), new Vector3(0,0,555), green)); // Example of adding a green quad
world.add(new Sphere(new Vector3(0, 1, 0), 1.0, material1));# Java-RTX
Basic java raytracer based on the Raytracing in one weekend series and some other code I stole off the internet. I have yet to make a proper sceneloader so deal with this hardcoded shit for now, hopefully I will make a proper sceneloader and API for this project at some point.

## API
Define a new scene like this:

`HittableList world = new HittableList();`
_____________________________________

Define materials like this
```java
Material green = new Lambertian(new Vector3(0.12, 0.45, 0.15));
Material metal = new Metal(new Vector3(0.7), 0.01);
Material light = new DiffuseLight(new Vector3(15));
```
For all material types look in `Material.java`

Lamberitans can be assigned textures, like this
```java
Material checker = new Lambertian(new Checker(0.32, new Vector3(0.2, 0.3, 0.1), new Vector3(0.9)));
```
_____________________________________

Add objects (anything that extends Hittable) using `world.add` like this
```java
world.add(new Quad(new Vector3(555,0,0), new Vector3(0,555,0), new Vector3(0,0,555), green)); // Example of adding a green quad
world.add(new Sphere(new Vector3(0, 1, 0), 1.0, material1));

Hittable box1 = Quad.box(new Vector3(0,0,0), new Vector3(165,330,165), metal);
box1 = new RotateY(box1, 15);
box1 = new Translate(box1, new Vector3(265,0,295));
world.add(box1);
```
Note that `boxes` are the only things that can be translated and rotated

You can also add smoke/volumes like this
```java
world.add(new ConstantMedium(box1, 0.01, new Vector3(0,0,0)));
world.add(new ConstantMedium(box2, 0.01, new Vector3(1,1,1)));
```

Note that `boxes` are the only things that can be translated and rotated

You can also add smoke/volumes like this
```java
world.add(new ConstantMedium(box1, 0.01, new Vector3(0,0,0)));
world.add(new ConstantMedium(box2, 0.01, new Vector3(1,1,1)));
```
Though they do look kinda wierd...

## Link to the original tutorials: 

https://raytracing.github.io/books/RayTracingInOneWeekend.html

https://raytracing.github.io/books/RayTracingTheNextWeek.html
