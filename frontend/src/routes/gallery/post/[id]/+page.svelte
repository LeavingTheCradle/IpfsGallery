<script>
	import axios from 'axios';
	import { onMount } from 'svelte';
	let gallery = {};
	let photos = [];
	/**
	 * @type {HTMLInputElement}
	 */
	let avatar, fileinput;

	onMount(async () => {});
	function upload() {
		alert('no more alerts');
	}
	const onFileSelected = (e) => {
		let image = e.target.files[0];
		let reader = new FileReader();
		reader.readAsDataURL(image);
		console.log(reader);

		reader.onload = (e) => {
			avatar = e.target.result;
		};
	};
</script>

<svelte:head>
	<title>About</title>
	<meta name="description" content="About this app" />
</svelte:head>

<div class="text-column">
	<input
		style="display:none"
		type="file"
		accept=".jpg, .jpeg, .png"
		on:change={(e) => onFileSelected(e)}
		bind:this={fileinput}
	/>
	<div id="image_post_div" class="flex items-center">
		<img id="image_post_preview" class="avatar" src={avatar} alt="" />
		<img
			image_post_upload_img
			class="upload"
			src="https://static.thenounproject.com/png/625182-200.png"
			alt=""
			on:click={() => {
				fileinput.click();
			}}
		/>
		<div
			id="image_post_add"
			class="chan"
			on:click={() => {
				fileinput.click();
			}}
		>
			Choose Image
		</div>
	</div>

	<button on:click|once={upload}>Upload</button>
	<form />
</div>

<style>
	#image_post_div {
		width: 20%;
	}
	#image_post_preview {
		width: 100%;
	}
	#image_post_upload_img {
		width: 100%;
	}
	#image_post_add {
		width: 100%;
	}
</style>
