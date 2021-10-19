extern crate image;

fn main() {
    let width = 800 as u32;
    let height = 600 as u32;
    let mut pixels = vec![0 as u8; (width * height * 4) as usize];

    unsafe {
        render(&mut pixels, width as u32, height as u32, 200);
    }
    
    image::save_buffer("image.png", &pixels, width, height, image::ColorType::Rgba8).unwrap()
}



const MAX_ITERATIONS: i32 = 60;
const RADIUS: f64 = 0.7885;

pub unsafe fn render<'a>(pixels: &mut [u8], width: u32, height: u32, angle: i32) {
    let total = width * height;
    let mid_x: f64 = width as f64 / 2.0;
    let mid_y: f64 = height as f64 / 2.0;

    let a: f64 = (angle as f64 % 360.0).to_radians();
    let c_x: f64 = RADIUS * a.cos();
    let c_y: f64 = RADIUS * a.sin();
    let val: f64 = 255 as f64 / (MAX_ITERATIONS as f64);

    for idx in 0..total {
        let x = (idx % width) as i32;
        let y = (idx / width) as i32;

        let mut zx: f64 = (2 * x - width as i32) as f64 / mid_x;
        let mut zy: f64 = (2 * y - height as i32) as f64 / mid_y;
        let mut i = 0;
        while i < MAX_ITERATIONS && (zx * zx + zy * zy) <= 4.0 {
            let tmp_zx: f64 = zx * zx - zy * zy + c_x;
            zy = 2.0 * zx * zy + c_y;
            zx = tmp_zx;
            i += 1;
        }
        let p_idx = 4 * idx as usize;
        pixels[p_idx] = (x * 255 / width as i32) as u8;
        pixels[p_idx + 1] = (val * i as f64) as u8;
        pixels[p_idx + 2] = (y * 255 / height as i32) as u8;
        pixels[p_idx + 3] = 255;
    }
}